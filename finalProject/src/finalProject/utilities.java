//roll 5 dices.
	public void rollDice() { 
		int diceFace;
		for (int i = 0; i<5; i++) {
			Random random = new Random(); 
	        diceFace = random.nextInt(6) + 1;
	        this.diceFaces[i]=diceFace;
		}
		System.out.println(Arrays.toString(diceFaces));
	}
	
	//to string 
	public String toString() {
		String toString = "This is player #" + this.id + ", name " + this.name +
				". The dices numbers are " + Arrays.toString(diceFaces) + 
				". The last bid is " + Arrays.toString(this.bid);
		System.out.println(toString);
		return toString;
	}