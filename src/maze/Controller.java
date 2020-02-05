package maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Controller implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game.start();
        this.drawBoard();
    }
    
    // Restart button code *****************************************************
    @FXML private Button restartBtn;
    @FXML private void restartClicked() {
        Game.loadLevel(Game.levelNum);
        this.drawBoard();
    }
    private static final String DARK = "-fx-background-color: #201d1b; -fx-text-fill: #ebedf1";
    private static final String LIGHT = "-fx-background-color: #ebedf1; -fx-text-fill: #201d1b";
    @FXML private void darkBtn() { restartBtn.setStyle(restartBtn.getStyle().replace(LIGHT, DARK)); }
    @FXML private void lightBtn() { restartBtn.setStyle(restartBtn.getStyle().replace(DARK, LIGHT)); }
    
    // Levels progress count ***************************************************
    @FXML private Label levelCount;
    @FXML private ProgressBar levelsProgress;
    public void changeLevel() {
        levelCount.setText("المرحلة : "+Game.levelNum);
        levelsProgress.setProgress((double) Game.levelNum/Game.NUMBER_OF_LEVELS);
        this.drawBoard();
    }
    
    // Game board code *********************************************************
    @FXML private GridPane grid;
    private static int currDim;
    private static final String PATH = "file:C:\\Users\\soud5\\Desktop\\Maze\\src\\img\\";
    void drawBoard() {
        // Removes older images
        grid.getChildren().clear();
        // Removes all rows and columns
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        
        currDim = 500/Game.size;
        
        while (grid.getRowConstraints().size() < Game.size) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints col = new ColumnConstraints();
            
            row.setPercentHeight(currDim);
            col.setPercentWidth(currDim);
            
            row.setValignment(VPos.CENTER);
            col.setHalignment(HPos.CENTER);
            
            grid.getRowConstraints().add(row);
            grid.getColumnConstraints().add(col);
        }
        
        grid.add(getImageView("player"), Game.p.x, Game.p.y);
        grid.add(getImageView("goal"), Game.g.x, Game.g.y);
        
        Game.blocks.forEach((block) -> {
            grid.add(getImageView("block"), block.x, block.y);
        });
        
        grid.gridLinesVisibleProperty().set(false);
        grid.gridLinesVisibleProperty().set(true);
    }
    private static ImageView getImageView(String entity) {
        ImageView img = new ImageView(PATH+entity+".png");
        img.setFitHeight(currDim-15);
        img.setFitWidth(currDim-15);
        return img;
    }
}
