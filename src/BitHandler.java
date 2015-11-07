
public class BitHandler {
	SerialWrite serial;
	BufferQueue behaviours;
	BitThread bitThread;
	
	public BitHandler() {
		serial = new SerialWrite();  //creates an object of the class
		serial.initialize();
		serial.portConnect();
		behaviours = new BufferQueue(20);
		(new Thread(new BitThread())).start();		
	}
	
	public void update(String s) {
		behaviours.push(s);
	}

	private class BitThread implements Runnable {

		@Override
		public void run() {
			send(180);
		}
		public void send(short s) {
			serial.write(s);
		}
		public void send(int i) {
			short s = (short) (i); 
			serial.write(s);
		}
		
	}
	// for testing
	public static void main(String args[]) {
        BitHandler b = new BitHandler();
    }
}
