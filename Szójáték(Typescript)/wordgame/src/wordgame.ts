
container: HTMLElement
var time: number = 59
var pause: boolean = false


function mw_init(initParams: InitParameter) {

    container = this.container

    this.start = startGame
    this.pause = timeStop
    this.resume = timeResume
    initParams.gameInitialized();

}

function timeStop() {
    pause = true
}

function timeResume() {
    pause = false
}

function startGame() {
    //Fő változók

    //szavak listája
    var wordsList: String[] = []
    var wordsTarolo: String[] = []

    var osszesSzo: any[] = []
    fetch('../wordgame/res/words.txt')
        .then(response => response.text())
        .then(data => {
            wordsList = data.split("\n")
            minden()
        }
        )

    function minden() {
        //pálya beállításainak változói
        let width = 3;
        let height = 3;
        //let buttonSize = 100
        let buttons: any[][] = []

        var tarolo: number[] = []

        var graph2: number[][] = [
            [0, 1, 0, 1, 1, 0, 0, 0, 0],
            [1, 0, 1, 1, 1, 1, 0, 0, 0],
            [0, 1, 0, 0, 1, 1, 0, 0, 0],
            [1, 1, 0, 0, 1, 0, 1, 1, 0],
            [1, 1, 1, 1, 0, 1, 1, 1, 1],
            [0, 1, 1, 0, 1, 0, 0, 1, 1],
            [0, 0, 0, 1, 1, 0, 0, 1, 0],
            [0, 0, 0, 1, 1, 1, 1, 0, 1],
            [0, 0, 0, 0, 1, 1, 0, 1, 0]
        ]

        class Hamilton {

            printSolution(solution: number[], size: number) {
                for (let i = 0; i < size - 1; i++) {
                    tarolo.push(solution[i])
                }
            }

            findSolution(graph: number[][], visited: boolean[], result: number[], node: number, counter: number, n: number, start: number) {
                if (counter == n && node == start) {
                    result[counter] = node;
                    this.printSolution(result, n + 1);
                }
                if (visited[node] == true) {
                    return;
                }
                // meglatogatott node-ok
                visited[node] = true;
                // eltarolja az utat
                result[counter] = node;
                for (let i = 0; i < n; ++i) {
                    if (graph[node][i] == 1) {
                        this.findSolution(graph, visited, result, i, counter + 1, n, start);
                    }
                }
                // reseteli a meglatogatott node-okat
                visited[node] = false;
            }

            setDefault(visited: boolean[], n: number) {
                for (let i = 0; i < n; ++i) {
                    visited[i] = false;
                }
            }

            hamiltonianCycle(graph: number[][], n: number) {
                // meglatogatott utak
                var visited: boolean[] = [];

                // eltarolja az ut informacioit
                var result: number[] = []
                for (let i = 0; i < n; ++i) {
                    this.setDefault(visited, n);
                    this.findSolution(graph, visited, result, i, 0, n, i);
                }
            }
        }


        var hamiltonian = new Hamilton();
        let n: number = 9;
        hamiltonian.hamiltonianCycle(graph2, n)

        let divCella = document.createElement("div")
        let divCim = document.createElement("div")

        //Szótároló
        let sequence = ""

        //Tábla amiben a pontok es ido van
        var cella = document.createElement('table');

        //Cim
        let cim = document.createElement("p")
        cim.textContent = "Szójáték"
        cim.style.fontSize = "300%"
        cim.style.color = "#ffab01"
        cim.style.textShadow = "0.7px 0.7px white"


        //Pont
        var pontCalc: number = 0
        let pont = document.createElement("p")
        pont.textContent = pontCalc.toString()

        //ido
        let ido = document.createElement("p")

        //cella melyben az ido es pont megjelenitve vannak
        var felsoSor = cella.insertRow(0)
        var alsoSor = cella.insertRow(1)
        var cellaSzoveg = felsoSor.insertCell(0)
        var cellaErtek = felsoSor.insertCell(1)
        var cellaIdoSzoveg = alsoSor.insertCell(0)
        var cellaIdo = alsoSor.insertCell(1)

        cellaSzoveg.innerHTML = "Pont: "
        cellaSzoveg.style.color = "#ffab01"
        cellaSzoveg.style.fontSize = "200%"
        cellaSzoveg.style.height = "5px"
        cellaSzoveg.style.textShadow = "0.7px 0.7px white"
        cellaSzoveg.style.marginLeft = "5px"

        cellaErtek.innerHTML = pont.textContent.toString()
        cellaErtek.style.color = "#ffab01"
        cellaErtek.style.textShadow = "0.7px 0.7px white"
        cellaErtek.style.fontSize = "200%"
        cellaErtek.style.height = "5px"
        cellaErtek.style.marginLeft = "5px"

        cellaIdoSzoveg.innerHTML = "Idő: "
        cellaIdoSzoveg.style.color = "#ffab01"
        cellaIdoSzoveg.style.textShadow = "0.7px 0.7px white"
        cellaIdoSzoveg.style.fontSize = "200%"
        cellaIdoSzoveg.style.height = "5px"
        cellaIdoSzoveg.style.marginLeft = "5px"

        //ido
        ido.textContent = time.toString()
        window.setInterval(idomeres, 1000);


        cellaIdo.innerHTML = ido.textContent.toString()
        cellaIdo.style.color = "#ffab01"
        cellaIdo.style.textShadow = "0.7px 0.7px white"
        cellaIdo.style.fontSize = "200%"
        cellaIdo.style.height = "5px"
        cellaIdo.style.marginLeft = "5px"

        function idomeres() {
            if (time != 0 && !pause) {
                time = time - 1
                ido.textContent = time.toString()
                cellaIdo.innerHTML = ido.textContent.toString()
            }
        }

        //cella megjelenitese a kepernyon
        divCim.appendChild(cim)
        divCella.appendChild(cella)
        container.appendChild(divCim)
        container.appendChild(divCella)

        let textDiv = document.createElement('div')
        //Összekötött betűk megjelenítése szóként
        let text = document.createElement("p")
        textDiv.appendChild(text)
        container.appendChild(textDiv)
        text.style.height = "5px"
        text.style.marginLeft = "5px"
        text.style.fontWeight = "bold"
        text.style.fontSize = "x-large"
        text.style.color = "white"
        text.style.textShadow = "0.7px 0.7px black"


        var clicked: number[][] = Array(3).fill(null).map(() => Array(3).fill(0));

        //lenullaza a clickelt gombokat, tehat reseteli a clickelheto gombokat
        function nullaz() {
            for (let i = 0; i < width; i++) {
                for (let j = 0; j < height; j++) {
                    clicked[i][j] = 0
                }
            }
        }

        let EnterButton = document.createElement("div")
        //Törlés gomb
        let clear = document.createElement("button")
        clear.style.width = "216px"
        clear.textContent = "Törlés"
        clear.style.height = "75px"
        clear.style.fontSize = "x-large"

        clear.onclick = (ev) => {
            if (time > 0 && pause == false) {
                sequence = ""
                text.textContent = sequence
                elozoI = -3
                elozoJ = -3
                nullaz()
                szinezes()
            }
        }

        //Beenterelt szó megjelenítés
        var table = document.createElement('table');
        let TableDiv = document.createElement('div')
        //Enter gomb
        let insert = document.createElement("button")
        insert.style.width = "216px"
        insert.textContent = "Szó bevitel"
        insert.style.height = "75px"
        insert.style.fontSize = "x-large"



        //bevitt szoert felelos gomb
        insert.onclick = (ev) => {
            if (time > 0 && pause == false) {
                osszesSzo.push(text.textContent.toString())
                var valt: any = text.textContent.toString()
                for (let i = 0; i < wordsTarolo.length; i++) {
                    if (valt == wordsTarolo[i].split("\r")[0] && valt != "") {
                        var row = table.insertRow(0)
                        var cell1 = row.insertCell(0)
                        cell1.innerHTML = valt
                        cell1.style.fontSize = "200%"
                        cell1.style.fontWeight = "bold"
                        cell1.style.color = "white"
                        cell1.style.textShadow = "0.7px 0.7px black"
                        pontCalc += valt.length
                        pont.textContent = pontCalc.toString()
                        cellaErtek.innerHTML = pont.textContent.toString()
                        wordsTarolo[i] = ""
                        sequence = ""
                        text.textContent = sequence
                        elozoI = -3;
                        elozoJ = -3;
                        jelenlegiI = 0;
                        jelenlegiJ = 0;
                        nullaz()
                        szinezes()
                        break
                    }
                }
            }
        }


        var segedTarolo: any[] = []
        var segedTarolo2: any[] = []

        //ket szo hosszaert felelos valtozok
        let randomLenghtWord = Math.floor(Math.random() * (6 - 3) + 3)
        let randomLenghtWord2 = 9 - randomLenghtWord

        console.log("Elso szo hossza: " + randomLenghtWord)
        console.log("Masodik szo hossza: " + randomLenghtWord2)

        function TwoWordMerge() {
            wordsTarolo = wordsList
            for (let i = 0; i < wordsList.length; i++) {
                if (wordsList[i].length == randomLenghtWord + 1)
                    segedTarolo.push(wordsList[i])
                if (wordsList[i].length == randomLenghtWord2 + 1)
                    segedTarolo2.push(wordsList[i])
            }

        }

        TwoWordMerge()

        //Random 2 szo indexe
        let randomIndex = Math.floor(Math.random() * (segedTarolo.length - 1) + 1)
        let randomIndex2 = Math.floor(Math.random() * (segedTarolo2.length - 1) + 1)

        //osszeillesztett 2 szo
        var randomWord = segedTarolo[randomIndex] + segedTarolo2[randomIndex2]

        console.log("Elso szo: " + segedTarolo[randomIndex])
        console.log("Masodik szo: " + segedTarolo2[randomIndex2])

        //whitespace es egyebb karakterek eltakaritasa a szo kozott
        randomWord = randomWord.replace(/\s/g, "")

        console.log("Ket szo egyutt: " + randomWord)

        var indexek: number[] = [0]
        for (let i = 1; i < randomWord.length - 1; i++) {
            indexek.push(i)
        }

        var indexTarolo: number[] = []

        for (let i = 0; i < tarolo.length; i++) {
            if (i % 9 == 0)
                indexTarolo.push(i)
        }

        function randomIndexGenerator(): number {
            return Math.floor(Math.random() * ((indexTarolo.length - 1) - 0) + 0)
        }

        var szaml: number = 0
        var randomIndexAlgoritmus: number = randomIndexGenerator()

        for (let i = 0; i < width; i++) {
            let div = document.createElement("div")
            div.style.padding = "0"
            container.appendChild(div)
            buttons.push([])
            for (let j = 0; j < height; j++) {

                let btn = document.createElement("button")
                btn.style.fontSize = "200%"

                var randomWordIndex: number = 0

                for (let u = indexTarolo[randomIndexAlgoritmus]; u < indexTarolo[randomIndexAlgoritmus] + 9; u++) {

                    if (szaml == tarolo[u]) {
                        btn.textContent = randomWord[randomWordIndex]
                    }
                    randomWordIndex++
                }
                szaml++
                div.appendChild(btn)
                buttons[i].push(btn)
            }
        }
        var elozoI = -3;
        var elozoJ = -3;
        var jelenlegiI = 0;
        var jelenlegiJ = 0;

        function szinezes() {
            for (let i = 0; i < width; i++) {
                for (let j = 0; j < height; j++) {
                    if (clicked[i][j] == 0)
                        buttons[i][j].style.backgroundColor = "#fff9c5"
                    else {
                        buttons[i][j].style.backgroundColor = "#ffce5d"
                    }
                    if (time == 0)
                        buttons[i][j].style.backgroundColor = "#fff9c5"

                }
            }
        }


        for (let i = 0; i < width; i++) {
            for (let j = 0; j < height; j++) {

                buttons[i][j].onclick = (ev: MouseEvent) => {
                    if (time > 0 && pause == false) {
                        if (clicked[i][j] == 0) {
                            jelenlegiI = i;
                            jelenlegiJ = j;
                            if (!((jelenlegiI == elozoI + 2 || jelenlegiI == elozoI - 2) || (jelenlegiJ == elozoJ + 2 || jelenlegiJ == elozoJ - 2))) {
                                elozoI = i;
                                elozoJ = j;
                                sequence += buttons[i][j].textContent
                                text.textContent = sequence
                                clicked[i][j] = 1
                                buttons[i][j].style.backgroundColor = "#ffce5d"
                            }
                        }
                    }
                }
            }
        }

        let refresh = document.createElement("button")
        refresh.textContent = '↻'//"Refresh"
        refresh.style.fontSize = "x-large"
        //refresh.style.background = "#ffce5d"
        refresh.onclick = (ev) => {
            window.location.reload()
        }
        refresh.style.width = "216px"
        refresh.style.height = "75px"
        EnterButton.appendChild(insert)
        EnterButton.appendChild(clear)
        EnterButton.appendChild(refresh)

        container.appendChild(EnterButton)
        TableDiv.appendChild(table)
        container.appendChild(TableDiv)
    }
}
