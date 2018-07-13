package com.kodilla.rps;

import java.util.Scanner;

public class Game {
    private final String SELECT = " selected ";
    private final char YES = 'y';
    private final char NO = 'n';
    private int computerScore = 0;
    private int playerScore = 0;
    private int numberOfGames = 0;
    private boolean end = false;
    Scanner scanner = new Scanner(System.in);

    public void resetGamePoints() {
        playerScore = 0;
        computerScore = 0;
        numberOfGames = 0;
    }

    public boolean endGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure want to end the game? \n YES = y \n NO = n");
        Character theEnd = scanner.next().charAt(0);
        if (theEnd == NO) {
            playAgain();
        }
        if (theEnd == YES) {
            end = true;
        }
        return end;
    }

    public boolean resetGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure want to reset Game, and play once again? \n YES = y \n NO = n");
        Character reset = scanner.next().charAt(0);
        if (reset != YES && reset != NO) {
            playAgain();
        }
        resetGamePoints();
        System.out.println("Setting are reset...\n");
        return reset == YES;
    }

    public boolean playAgain() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want play again? \n Yes = y \n NO = n");
        Character again = scanner.next().charAt(0);
        if (again == YES) {
            resetGamePoints();
            end = false;
            new Game();
            startGame();
        } else {
            processChoice();
        }
        return again == YES;
    }

    public  void display(String name, Choice what) {
        if (what != Choice.INVALID) {
            switch (what) {
                case ROCK:
                    System.out.println(name.toUpperCase() + SELECT + what);
                    break;
                case PAPER:
                    System.out.println(name.toUpperCase() + SELECT + what);
                    break;
                case SCISSOR:
                    System.out.println(name.toUpperCase() + SELECT + what);
            }
        }
    }


    public  int compere(Choice choicePlayer, Choice choiceComputer) {
        if (choicePlayer.equals(choiceComputer)) {
            System.out.println("Tie! \n");
            return 0;
        }
        switch (choicePlayer) {
            case ROCK:
                System.out.println("Sorry you louse :( \n");
                computerScore++;
                return (choiceComputer == Choice.SCISSOR ? 1 : -1);
            case PAPER:
                System.out.println("You win!!! :)\n");
                playerScore++;
                return (choiceComputer == Choice.ROCK ? 1 : -1);
            case SCISSOR:
                System.out.println("You win!!! :)\n");
                playerScore++;
                return (choiceComputer == Choice.PAPER ? 1 : -1);
            case INVALID:
                break;
        }
        return 0;
    }

    public Choice rpsChoice() {
        char playerChoice;
        System.out.println("Select: \nROCK = 1 \nPAPER = 2 \nSCISSOR = 3");
        playerChoice = scanner.next().charAt(0);

        switch (playerChoice) {
            case '1':
                return Choice.ROCK;
            case '2':
                return Choice.PAPER;
            case '3':
                return Choice.SCISSOR;
            default:
                return Choice.INVALID;
        }
    }

    public Choice processChoice() {
        System.out.println("START = 'a' \nEND GAME = 'x' \nRESET GAME = 'n'");
        char choiceWhat = scanner.next().charAt(0);
        switch (choiceWhat) {
            case 's':
                startGame();
                return Choice.START;
            case 'e':
                endGame();
                return Choice.END;
            case 'r':
                resetGame();
                return Choice.RESET;
            default:
                return Choice.INVALID;
        }
    }

    public void startGame() {
        Player player = new Player();
        Computer computer = new Computer();
        while(!end ) {
            Choice playerChoice = rpsChoice() ;
            if (playerChoice != Choice.INVALID) {
                display(player.getName(), playerChoice);
                Choice computerChoice = computer.computerChoice();
                display("COMPUTER", computerChoice);
                compere(playerChoice, computerChoice);
                }
            if (numberOfGames < player.getNumberOfRounds() - 1) {
                numberOfGames++;
            } else {
                end = true;
            }
        }
        if (!playAgain()) {
            printStats();
            processChoice();
        }
    }


    public void printStats() {
        System.out.println("Player score: " + playerScore);
        System.out.println("Computer score: " + computerScore);
        System.out.println("Number of rounds: " + numberOfGames);
        if (playerScore > computerScore) {
            System.out.println("You WIN!\n");
        }
        if (computerScore > playerScore) {
            System.out.println("Computer WON!\n");
        }
        if (playerScore == computerScore) {
            System.out.println("TIE! \n");
        }
    }

}
