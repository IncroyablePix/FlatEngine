package com.c4nn4.pix_engine.manager.debug;

import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

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

            String[] splits = text.split("\n");
            for (String split : splits) {
                if (cursor < MAX_SIZE) {
                    messages[cursor] = new Text(20, BASE_POSITION - (cursor * 22), -1, split, Color.RED, Fonts.COURIER_T);
                    cursor++;
                }
                else {
                    shiftMessages();
                    messages[MAX_SIZE - 1].setText(split);
                    //messages[0].setText(text);
                }
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
