public class peerSendThread implements Runnable {
	peerUtilites peerU;

	public peerSendThread(peerUtilites peerU) {
		this.peerU = peerU;
	}

	public void run() {
		String fromKeyboard = null;
		String message = null;
		try {
			while (!(fromKeyboard = peerU.readFromKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				message = peerU.getName() + " sent: " + fromKeyboard;
				//System.out.println(peerU.getName() + " sending");
				peerU.sendToSocket(message);
				//fromSocket = peerU.readFromSocket();
				//peerU.sendToTerminal(fromSocket);
			}

		} catch (Exception E) {
		}
	}
}
