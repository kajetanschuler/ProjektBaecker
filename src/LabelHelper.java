import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

// Class to extend Label for easier dot animations
public class LabelHelper extends Label {
    private Timeline timeline;

    public LabelHelper(String text) {
        super(text);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    // set Timeline Object for this Label
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    // Stop animation
    public void pauseTimeline () {
        if (timeline != null) {
            timeline.pause();
        }

    }

    // Animate three appearing dots after given text
    public void animateLabelText(String initialText, String endText) {
        // create animation for NFC Label
        super.setText(initialText);
        pauseTimeline();
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler() {
                    @Override public void handle(Event event) {
                        String statusText = LabelHelper.super.getText();
                        setText(
                                (endText.equals(statusText))
                                        ? initialText
                                        : statusText + " ."
                        );
                    }
                }),
                new KeyFrame(Duration.millis(350))
        );
        setTimeline(timeline);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}

