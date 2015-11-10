import java.util.concurrent.TimeUnit;


public class BitHandler {
	SerialWrite serial;
	BufferQueue behaviours;
	BitThread bitThread;
	double t = 0;
	double hz = 0.01;
	double amp = 90;
	double offset = 90;
	public BitHandler() {
		
		behaviours = new BufferQueue(20);
		bitThread = new BitThread();
		new Thread(bitThread).start();		
	}
	
	public void update(String s) {
		behaviours.push(s);
	}
	
	
	public int sine() {
		double s = (amp * Math.sin(t*Math.PI)) + offset;
		int i = (int) s;
		return i;
	}

	private class BitThread implements Runnable {
		int count = 0;
		@Override
		public void run() {
			serial = new SerialWrite();  //creates an object of the class
			serial.initialize();
			serial.portConnect();
			
			loop(-1);
			
			serial.close();
			System.out.println("done sending fer now");
		}
		public void loop(int i) {
			int j = 0;
			if (i < 0) {
				while(true) {
					send(sine());
//					 2 seconds per period
					try {
						TimeUnit.MILLISECONDS.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t = t + hz;
					System.out.println(sine());
				}
			}
			while (j < i) {
				j++;
				send(180);
			} 
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