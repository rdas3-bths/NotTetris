public class SoundTester {
    public static void main(String[] args) {
        try {
            SoundHandler s = new SoundHandler();
            s.playSound1();
            Thread.sleep(1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
