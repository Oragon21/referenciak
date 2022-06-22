package Game.Graphics.PlayerButton;

import Game.Agents.Virus.GrizzlyVirus;
import Game.Agents.Virus.ParalyzingVirus;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerButton extends JButton {
	private Player player;
	private Map map;

	public PlayerButton(Player p, Map map) {
		player = p;
		this.map = map;
		init();
	}

	private void init() {
		setBounds(0, 0, 30, 30);
		addActionListener(e -> map.handlePlayerPressed(player));
	}

	public void setPosition(int x, int y) {
		Rectangle curr = this.getBounds();
		this.setBounds(x, y, curr.width, curr.height);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public Color getBackground() {
		if (player == null) {
			return Color.GRAY;
		}
		if (player.isCurrent()) {
			return Color.GREEN;
		}
		if (player.getInfectedBy() != null) {
			if (player.getInfectedBy().getClass() == ParalyzingVirus.class) {
				return Color.BLACK;
			}
			if (player.getInfectedBy().getClass() == GrizzlyVirus.class) {
				return Color.RED;
			}
		}

		return Color.GRAY;
	}

	@Override
	protected void paintComponent(Graphics g) {
		boolean currentTurn = map.getCurrentPlayer().equals(player);
		boolean standingOnTheSameTile = map.getCurrentPlayer().getActTile().getTouchable().contains(player);
		boolean standingOnAdjacenTile = map.getCurrentPlayer().getActTile().getNeighbours().contains(player.getActTile());
		if (currentTurn || standingOnTheSameTile || standingOnAdjacenTile) {
			super.paintComponent(g);
		}
	}

	@Override
	protected void paintBorder(Graphics g) {
		boolean currentTurn = map.getCurrentPlayer().equals(player);
		boolean standingOnTheSameTile = map.getCurrentPlayer().getActTile().getTouchable().contains(player);
		boolean standingOnAdjacenTile = map.getCurrentPlayer().getActTile().getNeighbours().contains(player.getActTile());
		if (currentTurn || standingOnTheSameTile || standingOnAdjacenTile) {
			super.paintBorder(g);
		}
	}
}
