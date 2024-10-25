
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class kazino extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JProgressBar progressBar1;

    public kazino() {
        JFrame frame = new JFrame("PichagaBET");
        frame.setDefaultCloseOperation(3);
        frame.setSize(500, 500);
        frame.add(this.panel1);
        frame.setVisible(true);
        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kazino.this.button1.setEnabled(false);
                kazino.this.progressBar1.setMaximum(36);
                Random rand = new Random();
                int rand_int1 = rand.nextInt(37);
                (kazino.this.new ProgressTask(rand_int1)).execute();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(kazino::new);
    }

    private class ProgressTask extends SwingWorker<Void, Integer> {
        private int rand;

        public ProgressTask(int rand) {
            this.rand = rand;
        }

        protected Void doInBackground() {
            for(int i = 0; i <= this.rand; ++i) {
                this.publish(new Integer[]{i});

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException var3) {
                    InterruptedException e = var3;
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void process(List<Integer> chunks) {
            Iterator var2 = chunks.iterator();

            while(var2.hasNext()) {
                int value = (Integer)var2.next();
                kazino.this.progressBar1.setValue(value);
                kazino.this.button1.setText(Integer.toString(value));
                if (value % 2 == 0) {
                    kazino.this.progressBar1.setForeground(Color.black);
                } else {
                    kazino.this.progressBar1.setForeground(Color.red);
                }
            }

        }

        protected void done() {
            kazino.this.button1.setEnabled(true);
        }
    }
}
