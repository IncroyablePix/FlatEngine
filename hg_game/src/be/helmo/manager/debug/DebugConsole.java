package be.helmo.manager.debug;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.texts.Text;
import be.helmo.main.screen.Screen;
import be.helmo.manager.FontManager;

import java.awt.*;

/**
 * DebugConsole
 * <p>
 * Shows the last debug messages
 *
 * @author IncroyablePix
 */
public class DebugConsole {

    private static final byte MAX_SIZE = 15;
    private static final int BASE_POSITION = Screen.WIN_HEIGHT - 30;// - (MAX_SIZE * 22);

    private final Text[] messages;
    private byte cursor;

    public DebugConsole() {
        cursor = 0;
        messages = new Text[MAX_SIZE];

        write("Debug console initiated");
    }

    public void write(final String text) {
        if (text != null) {
            System.out.println(text);

            if (cursor < MAX_SIZE) {
                messages[cursor] = new Text(20, BASE_POSITION - (cursor * 22), -1, text, Color.RED, FontManager.COURIER_T);
                cursor++;
            }
            else {
                shiftMessages();
                messages[MAX_SIZE - 1].setText(text);
                //messages[0].setText(text);
            }
        }
    }

    public void draw(final Renderer renderer) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (messages[i] != null) {
                messages[i].draw(renderer);
            }
        }
    }

    private void shiftMessages() {
        String oldText;
		
		/*for(byte i = MAX_SIZE - 1; i >= 0; i --) {
			oldText = messages[i].getText();
			if(i != MAX_SIZE - 1) {
				messages[i + 1].setText(oldText);
			}
		}*/
        for (byte i = 0; i < MAX_SIZE; i++) {
            oldText = messages[i].getText();
            if (i != 0) {
                messages[i - 1].setText(oldText);
            }
        }
    }

	/*public void writeError(String text) {
		
		//textArea.append(String.valueOf((char) arg0));
		textArea.setText("<html><p style=\"color:#CC0000\">" + textArea.getText() + text + "\n"/* + "</p></html>\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.update(textArea.getGraphics());
		
		System.err.println(text);
	}*/

}
