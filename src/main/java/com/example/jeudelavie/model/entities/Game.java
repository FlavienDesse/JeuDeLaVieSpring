package com.example.jeudelavie.model.entities;

public class Game {

    private int[][] board;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }


    public int[][] calculateNextStep() {
        int[][] res = new int[this.board.length][this.board[0].length];


        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                int nbCellAlive = 0;

                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {


                        if (k != 0 || l != 0) {
                            int indexX = i + k;
                            int indexY = j + l;

                            if (indexX < 0) {
                                indexX = this.board.length - 1;
                            } else if (indexX >= this.board.length) {
                                indexX = 0;
                            }

                            if (indexY < 0) {
                                indexY = this.board[0].length - 1;
                            } else if (indexY >= this.board[0].length) {
                                indexY = 0;
                            }




                            nbCellAlive += this.board[indexX][indexY];


                        }
                    }
                }

                if (nbCellAlive == 3) {
                    res[i][j] = 1;
                } else if (nbCellAlive < 2 || nbCellAlive > 3) {
                    res[i][j] = 0;
                }
                else{
                    res[i][j]=this.board[i][j];
                }



            }
        }

        return res;
    }

}
