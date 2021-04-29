(function () {
    const parent = $("#containerCanvas")
    const canvas = $("#board")
    const ctx = canvas[0].getContext('2d');

    ctx.canvas.height = parent.height();
    ctx.canvas.width = parent.width();
    let timer;
    let gameValue = {
        sizeWidth: 20,
        sizeHeight: 20,
        nbCellHeight: 20,
        nbCellWidth: 20,
        speed: 50,
        start: false,
        autoplay:false,
        board: Array.from({length: 20}, _ => new Array(20).fill(0)),
    }


    const centerCanvas = {
        x: Math.round(ctx.canvas.width / 2),
        y: Math.round(ctx.canvas.height / 2),
    }
    const sizeBoard = {
        width: gameValue.sizeWidth * gameValue.nbCellWidth,
        height: gameValue.sizeHeight * gameValue.nbCellHeight,
    }


    const startBoard = {
        x: Math.round(centerCanvas.x - sizeBoard.width / 2),
        y: Math.round(centerCanvas.y - sizeBoard.height / 2),
    }
    const endBoard = {
        x: Math.round(centerCanvas.x + sizeBoard.width / 2),
        y: Math.round(centerCanvas.y + sizeBoard.height / 2),
    }


    const inputSizeHeight = $("#inputSizeHeight");
    const inputSizeWidth = $("#inputSizeWidth");

    const inputNbCellHeight = $("#inputNbCellHeight");
    const inputNbCellWidth = $("#inputNbCellWidth");

    const buttonStart = $("#buttonStart");

    const inputSpeed = $("#inputSpeed");

    const rowSpeed = $("#rowSpeed");

    const inputAutoPlay = $("#inputAutoPlay");

    inputSpeed.val(gameValue.speed)

    inputSizeHeight.val(gameValue.sizeWidth)
    inputSizeWidth.val(gameValue.sizeHeight)

    inputNbCellHeight.val(gameValue.nbCellHeight)
    inputNbCellWidth.val(gameValue.nbCellWidth)

    inputAutoPlay.click((e) => {
        rowSpeed.css("display", e.target.checked ? "" : "none");
        gameValue.autoplay = e.target.checked
    })

    gameValue.autoplay = inputAutoPlay.prop("checked")
    rowSpeed.css("display", inputAutoPlay.prop("checked") ? "" : "none");




    buttonStart.click((e) => {





        if (gameValue.start) {
            buttonStart.removeClass("btn-secondary").addClass("btn-primary")
            buttonStart.html("Run")
            clearInterval(timer)
        } else {
            if(gameValue.autoplay){
                buttonStart.removeClass("btn-primary").addClass("btn-secondary")
                buttonStart.html("Stop")
                timer =  setInterval(runOneCycle, gameValue.speed);
            }
            else{
                runOneCycle()
                gameValue.start = true
            }

        }
        gameValue.start = !gameValue.start







    })

    const runOneCycle= ()=>{
        $.post( "game", {board:gameValue.board})
            .done(function( data ) {
                gameValue.board = data
                drawBoard()
            });
    }

    const refreshValue = () => {
        centerCanvas.x = Math.round(ctx.canvas.width / 2)
        centerCanvas.y = Math.round(ctx.canvas.height / 2)

        sizeBoard.width = gameValue.sizeWidth * gameValue.nbCellWidth;
        sizeBoard.height = gameValue.sizeHeight * gameValue.nbCellHeight;

        startBoard.x = Math.round(centerCanvas.x - sizeBoard.width / 2);
        startBoard.y = Math.round(centerCanvas.y - sizeBoard.height / 2);

        endBoard.x = Math.round(centerCanvas.x + sizeBoard.width / 2);
        endBoard.y = Math.round(centerCanvas.y + sizeBoard.height / 2);
    }

    const drawStrokeBoard = () => {

        for (let i = 0; i < gameValue.nbCellHeight + 1; i++) {
            ctx.moveTo(startBoard.x, startBoard.y + i * gameValue.sizeHeight);
            ctx.lineTo(endBoard.x, startBoard.y + i * gameValue.sizeHeight);
        }
        for (let i = 0; i < gameValue.nbCellWidth + 1; i++) {
            ctx.moveTo(startBoard.x + i * gameValue.sizeWidth, startBoard.y);
            ctx.lineTo(startBoard.x + i * gameValue.sizeWidth, endBoard.y);
        }
        ctx.stroke();
    }

    canvas.click((e) => {
        const cursorX = e.pageX;
        const cursorY = e.pageY;

        const indexX = Math.ceil((cursorX - startBoard.x) / gameValue.sizeWidth) - 1;
        const indexY = Math.ceil((cursorY - startBoard.y) / gameValue.sizeHeight) - 1;

        if ((indexX >= 0 && indexX < gameValue.nbCellWidth) ||  (indexY >= 0 && indexY < gameValue.nbCellHeight)) {
            gameValue.board[indexX][indexY] = 1 - gameValue.board[indexX][indexY]
            drawBoard()
        }

    })

    const drawCell = () => {
        for (let i = 0; i < gameValue.nbCellWidth; i++) {
            for (let j = 0; j < gameValue.nbCellHeight; j++) {
                if (gameValue.board[i][j]) {
                    ctx.fillRect(startBoard.x + i * gameValue.sizeWidth, startBoard.y + j * gameValue.sizeHeight, gameValue.sizeWidth, gameValue.sizeHeight);
                }

            }
        }
    }

    const drawBoard = () => {
        ctx.canvas.height = parent.height();
        drawStrokeBoard()
        drawCell()
    }


    const updateSizes = function () {
        ctx.canvas.height = parent.height();
        ctx.canvas.width = parent.width();
        refreshValue()
        drawBoard()
    };

    inputSizeHeight.change((e) => {
        ctx.canvas.height = parent.height();
        gameValue.sizeHeight = parseInt(e.target.value)

        refreshValue()
        drawBoard()
    })

    inputNbCellHeight.change((e) => {
        ctx.canvas.height = parent.height();
        gameValue.nbCellHeight = parseInt(e.target.value)
        let res=Array.from({length:  gameValue.nbCellWidth}, _ => new Array(gameValue.nbCellHeight).fill(0));
        for (let i = 0; i < gameValue.nbCellWidth; i++) {
            for (let j = 0; j < gameValue.nbCellHeight; j++) {
                try{
                    res[i][j] = gameValue.board[i][j]
                }
                catch (e){
                    res[i][j] =0
                }
            }
        }
        gameValue.board = res




        refreshValue()
        drawBoard()
    })

    inputNbCellWidth.change((e) => {
        ctx.canvas.height = parent.height();
        gameValue.nbCellWidth = parseInt(e.target.value)
        let res=Array.from({length:  gameValue.nbCellWidth}, _ => new Array(gameValue.nbCellHeight).fill(0));
        for (let i = 0; i < gameValue.nbCellWidth; i++) {
            for (let j = 0; j < gameValue.nbCellHeight; j++) {
                try{
                    res[i][j] = gameValue.board[i][j]
                }
                catch (e){
                    res[i][j] =0
                }
            }
        }
        gameValue.board = res

        refreshValue()
        drawBoard()
    })

    inputSpeed.change((e)=>{
        gameValue.speed = parseInt(e.target.value)
    })

    inputSizeWidth.change((e) => {
        ctx.canvas.height = parent.height();
        gameValue.sizeWidth = parseInt(e.target.value)
        refreshValue()
        drawBoard()
    })

    $(window).on('resize', updateSizes);
    drawStrokeBoard()

}());



