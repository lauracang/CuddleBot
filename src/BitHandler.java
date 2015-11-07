
public class BitHandler {
	SerialWrite serial;
	BufferQueue behaviours;
	BitThread bitThread;
	
	public BitHandler() {
		
		behaviours = new BufferQueue(20);
		bitThread = new BitThread();
		new Thread(bitThread).start();		
	}
	
	public void update(String s) {
		behaviours.push(s);
	}

	private class BitThread implements Runnable {
		int count = 0;
		@Override
		public void run() {
			serial = new SerialWrite();  //creates an object of the class
			serial.initialize();
			serial.portConnect();
			
			loop(10000);
			
			serial.close();
			System.out.println("done sending fer now");
		}
		public void loop(int i) {
			int j = 0;
			while (j < i) {
				j++;
				send(180);
			}
			//TODO 
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