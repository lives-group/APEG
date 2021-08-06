import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Classe JPanel do score que fica no canto superior direito do jogo.
 * 
 * @author mhadaniya
 * @version 20/05
 */
public class Score extends JPanel
{
    private int score;
    private Font font;
    
    /**
     * Constructor for objects of class Score
     */
    public Score()
    {
        score = 0;
    }
   
   
     /**
     * Este método soma o número de pontos ao score.
     * 
     * @param  points   número de pontos 
     * @return     void
     */
    public void addScore(int points){
        this.score = this.score + points;
    }
    
    /**
    * Este método subtrai o número de pontos ao score.
    * 
    * @param  points   número de pontos 
    * @return     void
    */
    public void subScore(int points){
        this.score = this.score + points;
    }
    
    /**
    * Este método desenha o score 
    * 
    * @param  Graphics g    
    * @return     void
    */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try{
            File file = new File("fonts/VT323-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            font = font.deriveFont(Font.PLAIN,36);
            g2d.setFont(font);
        }catch (Exception e){
            System.out.println(e.toString());
        }   
        g2d.drawString("Score: " + this.score, 600, 50);
    }

    /**
    * Override do método que pinta este componente, pois ele é um JPanel.
    * 
    * @param  Graphics g    
    * @return     void
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
}
