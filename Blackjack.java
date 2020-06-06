import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class Blackjack extends ConsoleProgram {

	private boolean playerAcePresent, dealerAcePresent, playAgain, invalidBet, playerBusted;
	private int coins, bet, playerTotal, dealerTotal, card;
	private ArrayList<Integer> cardsLeft, playerCards, dealerCards;

	public void run() {
		cardsLeft = new ArrayList<Integer>();
		playerCards = new ArrayList<Integer>();
		dealerCards = new ArrayList<Integer>();
		playAgain = true;
		coins = 1000;
		printInstructions();
		addCardsToDeck();
		while (coins > 0 && playAgain) {
			simulateRound();
			resetDeck();
			println();
			String playAgainString = readLine(">>Would you like to play again?: ");
			if (playAgainString.equals("yes") || playAgainString.equals("Yes")) {
				playAgain = true;

			} else {
				playAgain = false;
			}
		}
		if (!playAgain) {
			println(">>Have a good day. Sorry to see you go. :(");
		} else if (coins < 1) {
			println(">>Sorry you're bankrupt :(");
		}
	}

	public void printInstructions() {
		println(">>Welcome to David Casino");
		println(">>This is Blackjack");
		println(">>Your goal is to get a combination of cards equal or closest to 21");
		println(">>There is a dealer who you will try to beat");
		println(">>You will start of with a thousand dollars");
		println(">>Good luck");
	}

	public void addCardsToDeck() {
		for (int i = 1; i < 14; i++) {
			for (int j = 0; j < 4; j++) {
				cardsLeft.add(i);
			}
		}

	}

	public int generateCard() {
		int cardSpot = 0;
		cardSpot = (int) (Math.random() * cardsLeft.size());
		card = cardsLeft.get(cardSpot);
		cardsLeft.remove(cardSpot);
		return card;
	}

	public void simulateRound() {
		playerAcePresent = false;
		dealerAcePresent = false;
		playerBusted = false;
		invalidBet = true;
		playerTotal = 0;
		dealerTotal = 0;
		println();
		while (invalidBet) {
			bet = readInt(">>How much would you like to bet? You have " + coins + " dollars remaining: ");
			if (bet > 0 && coins >= bet) {
				invalidBet = false;
			}
		}
		playerCards.add(generateCard());
		playerCards.add(generateCard());
		dealerCards.add(generateCard());
		dealerCards.add(generateCard());
		print(">>You cards are: ");
		printPlayerCards();
		if (playerTotal != 21) {
			print(">>The dealer's first card is a: ");
			printDealerCard();
			playerTurn();
			if (!playerBusted) {
				dealerTurn();
			}
			if (playerTotal < 22 && dealerTotal < 22) {
				if (playerTotal > dealerTotal) {
					println(">>Congrats, you won!");
					coins += bet;
					println(">>You now have " + coins + " dollars left");
				} else if (dealerTotal > playerTotal) {
					println(">>Sorry, you lost");
					coins -= bet;
					println(">>You now have " + coins + " dollars left");
				} else {
					println(">>You have tied the dealer");
					println(">>You still have " + coins + " dollars ");
				}
			}
		}

	}

	public void printPlayerCards() {
		for (int i = 0; i < 2; i++) {
			if (playerCards.get(i) == 11) {
				print("jack ");
				playerTotal += 10;
			} else if (playerCards.get(i) == 12) {
				print("queen ");
				playerTotal += 10;
			} else if (playerCards.get(i) == 13) {
				print("king ");
				playerTotal += 10;
			} else if (playerCards.get(i) == 1) {
				print("ace ");
				playerAcePresent = true;
			} else {
				print(playerCards.get(i) + " ");
				playerTotal += playerCards.get(i);
			}

		}
		if (playerAcePresent) {
			println();
			int ace = readInt(">>Would you like your ace to be a 1 or 11?: ");
			playerTotal += ace;

		}
		println();
		println(">>Your total is: " + playerTotal);
		if (playerTotal == 21) {
			println(">>Congrats, you got blackjack!");
			println(">>You get double your bet in return!");
			coins += bet * 2;
			println(">>You now have " + coins + " dollars");
		}
	}

	public void printDealerCard() {
		if (dealerCards.get(0) == 11) {
			print("jack");
			dealerTotal += 10;
		} else if (dealerCards.get(0) == 12) {
			print("queen");
			dealerTotal += 10;
		} else if (dealerCards.get(0) == 13) {
			print("king");
			dealerTotal += 10;
		} else if (dealerCards.get(0) == 1) {
			print("ace");
			dealerAcePresent = true;
		} else {
			print(dealerCards.get(0));
			dealerTotal += dealerCards.get(0);
		}
	}

	public void playerTurn() {
		int numberOfHits = 0;
		println();
		int hit = readInt(">>For another card, type 1, to stay, type 2: ");
		while (hit == 1 && playerTotal < 22) {
			numberOfHits++;
			playerCards.add(generateCard());
			print(">>Your card is: ");
			if (playerCards.get(1 + numberOfHits) == 11) {
				print("jack ");
				playerTotal += 10;
			} else if (playerCards.get(1 + numberOfHits) == 12) {
				print("queen ");
				playerTotal += 10;
			} else if (playerCards.get(1 + numberOfHits) == 13) {
				print("king ");
				playerTotal += 10;
			} else if (playerCards.get(1 + numberOfHits) == 1) {
				print("ace ");
				println();
				int ace = readInt(">>Would you like your ace to be a 1 or 11?: ");
				playerTotal += ace;
			} else {
				print(playerCards.get(1 + numberOfHits));
				playerTotal += playerCards.get(1 + numberOfHits);
			}
			if (playerTotal > 21) {
				println();
				println(">>Sorry, you busted");
				coins -= bet;
				println(">>You now have " + coins + " dollars left");
			} else {
				println();
				println(">>Your total is: " + playerTotal);
				hit = readInt(">>For another card, type 1, to stay, type 2: ");
			}
		}
	}

	public void dealerTurn() {
		int numberOfHits = 0;
		print(">>The dealer's second card is a: ");
		if (dealerCards.get(1) == 11) {
			print("jack");
			dealerTotal += 10;
		} else if (dealerCards.get(1) == 12) {
			print("queen");
			dealerTotal += 10;
		} else if (dealerCards.get(1) == 13) {
			print("king");
			dealerTotal += 10;
		} else if (dealerCards.get(1) == 1) {
			print("ace");
			if (dealerTotal < 11) {
				dealerTotal += 11;
			} else {
				dealerTotal += 1;
			}
		} else {
			print(dealerCards.get(1));
			dealerTotal += dealerCards.get(1);
		}
		println();
		println(">>The dealer total is " + dealerTotal);

		while (dealerTotal < 17) {
			println(">>By rule, if the dealer has under 17 they must hit");
			numberOfHits++;
			dealerCards.add(generateCard());
			print(">>The dealer's card is a: ");
			if (dealerCards.get(1 + numberOfHits) == 11) {
				println("jack");
				dealerTotal += 10;
			} else if (dealerCards.get(1 + numberOfHits) == 12) {
				println("queen");
				dealerTotal += 10;
			} else if (dealerCards.get(1 + numberOfHits) == 13) {
				println("king");
				dealerTotal += 10;
			} else if (dealerCards.get(1 + numberOfHits) == 1) {
				println("ace");
				if (dealerTotal < 11) {
					dealerTotal += 11;
				} else {
					dealerTotal += 1;
				}
			} else {
				println(dealerCards.get(1 + numberOfHits));
				dealerTotal += dealerCards.get(1 + numberOfHits);
			}
			println(">>The dealer total is " + dealerTotal);

		}
		if (dealerTotal > 21) {
			println(">>The dealer busted. Congrats, you won");
			coins += bet;
			println(">>You now have " + coins + " dollars left");
		} else {
			println(">>By rule, the dealer must stay if they have a 17 or over.");
		}

	}

	public void resetDeck() {
		while (cardsLeft.size() != 0) {
			cardsLeft.remove(0);
		}
		addCardsToDeck();

	}

}
