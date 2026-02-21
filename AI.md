# Ai assistance

## DialogBox class
1. To crop the picture file to be a circle

```
 // change the picture file to be a circle
    public void initialize() {
        double radius = 50;

        Circle clip = new Circle(radius, radius, radius);
        displayPicture.setClip(clip);

        displayPicture.setFitWidth(radius * 2);
        displayPicture.setFitHeight(radius * 2);
    }
```