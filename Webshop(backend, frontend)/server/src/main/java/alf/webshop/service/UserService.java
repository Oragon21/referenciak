package alf.webshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import alf.webshop.domain.Address;
import alf.webshop.domain.Cart;
import alf.webshop.domain.ERole;
import alf.webshop.domain.Product;
import alf.webshop.domain.Role;
import alf.webshop.domain.User;
import alf.webshop.repository.UserRepository;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final CartService cartService;
    private final AddressService addressService;
    private final RoleService roleService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, CartService cartService, AddressService addressService, RoleService roleService, ProductService productService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.addressService = addressService;
        this.roleService = roleService;
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExistsByRole(ERole eRole){
        Optional<Role> role = roleService.getRole(eRole);
        HashSet<Role> roleSet = new HashSet<Role>(
            Arrays.asList(role.get())
        );
        return userRepository.existsByRolesIn(roleSet);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new EntityNotFoundException(String.format("User with id %d does not exist in database", userId));
        }
        return user.get();
    }

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            logger.warn("Mar letezik ilyen");
            throw new EntityNotFoundException(String.format("User with username %s does not exist in database", username));
        }
        return user.get();
    }

    public User createUser(User user){
        return createUserByRole(user, ERole.ROLE_USER);
    }

    public User createAdminUser(User user){
        return createUserByRole(user, ERole.ROLE_ADMIN);
    }

    public void buyCartContent(Cart cart) {
        List<Product> products = cart.getProducts();
       // cartService.updateCart(cart);
        for (Product product : products) {
            productService.deleteProductById(product.getId());
        }
        cart.setProducts(new ArrayList<Product>());
        cartService.updateCart(cart, cart);
    }

    public User updateUser(User user, User newData){
        user.copyvalidValuesFrom(newData);
        if(newData.getPassword() != null)
            user.setPassword(passwordEncoder.encode(newData.getPassword()));
        if(newData.getHomeAddress() != null){
            addressService.updateAddress(user.getHomeAddress(), newData.getHomeAddress());
        }
        if(newData.getCart() != null){
            cartService.updateCart(user.getCart(), newData.getCart());
        }
        if(newData.getBillingAddress() != null){
            if(newData.getBillingAddress() != null) {
                if(user.getBillingAddress() == null) { // if user didnt have billing address before, create one
                    Address address = addressService.createAddress(newData.getBillingAddress());
                    user.setBillingAddress(address);
                }
                else {
                    addressService.updateAddress(user.getBillingAddress(), newData.getBillingAddress());
                }
            }
        }
        else {
            if(user.getBillingAddress() != null) {
                Address billingAddress = user.getBillingAddress();
                user.setBillingAddress(null);
                user = userRepository.save(user);
                addressService.deleteAddress(billingAddress.getId());
                return user;
            }
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(String.format("User with id %d does not exist in database", userId));
        }
        userRepository.deleteById(userId);
    }

    public void deleteUserByUsername(String username){
        if(!userRepository.existsByUsername(username)){
            throw new EntityNotFoundException(String.format("User with username %s does not exist in database", username));
        }
        userRepository.deleteByUsername(username);
    }

    private User createUserByRole(User user, ERole eRole){
        // Check if user would be unique
        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityExistsException(String.format("User with username %s already exists", user.getUsername()));
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EntityExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        // set address
        Address homAddress = addressService.createAddress(user.getHomeAddress());
        user.setHomeAddress(homAddress);
        // set billing address
        if(user.getBillingAddress() != null) {
            Address billingAddress = addressService.createAddress(user.getBillingAddress());
            user.setBillingAddress(billingAddress);
        }
        // set cart
        Cart cart = cartService.createCart();
        user.setCart(cart);
        // set role
        Optional<Role> role = roleService.getRole(eRole);
        user.setRoles(new HashSet<Role>(
            Arrays.asList(role.get())
        ));
        // encrypt password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        // save user
        return userRepository.save(user);
    }
}
