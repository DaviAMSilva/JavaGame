package davi.game;

public class TextBox extends GameObject {
    private String text;

    @Override
    public void draw(GameRenderer gr) {
        gr.textSize(GameData.TEXTBOX_TEXT_SIZE);
        
        // Makes a outline of the text
        gr.fill(GameData.TEXTBOX_TEXT_OUTLINE_COLOR);
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                gr.text(text, position.x + i, position.y + j);
            }
        }

        gr.fill(GameData.TEXTBOX_TEXT_COLOR);
        gr.text(text, position.x, position.y);
    }

    public TextBox(float x, float y, float width, float height, String text) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
