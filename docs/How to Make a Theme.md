# How to Make a Theme

If you have not yet read the _Features_ section of the README, go back and read it. It tells you where to find the `themes` directory.

## Step 1 - Familiar?

Inside the `themes` directory, create a new file named `mytheme.css`. If you have done web development before, you will know what CSS is. In this case, we are using `JavaFX CSS`!

## Step 2 - Template time!

In the file you just created, paste this CSS block:

```css
.root {
  -fx-base: #1b0b2e;
  -fx-background: #1b0b2e;
  -fx-control-inner-background: #1b0b2e;
  -fx-text-fill: #f0eaff;
  -fx-accent: #8a5cff;
  -fx-focus-color: #7c4dff;
  -fx-faint-focus-color: #7c4dff44;
}

.text-area {
  -fx-background-color: transparent;
  -fx-text-fill: #f0eaff;
  -fx-font-family: "Arial";
  -fx-font-size: 16px;
  -fx-padding: 8px;
}

.text-area .content {
  -fx-background-color: #2a1043;
  -fx-padding: 8px;
  -fx-line-spacing: 10px;
}

#titleBar {
  -fx-background-color: #7c4dff44;
}

#titleBar .button {
  -fx-padding: 0px 8px;
  -fx-font-family: "Arial";
  -fx-font-size: 16px;
  -fx-background-color: transparent;
  -fx-cursor: hand;
}

.scroll-bar:vertical,
.scroll-bar:horizontal {
  -fx-background-color: #3f2a58;
}

.thumb {
  -fx-background-color: #8a5cff;
}
```

Once pasted, you probably don't realize it, but that's the entire `cool-purple` theme.

## Step 3 - Play around with it!

Try changing some colors! To be honest, even as the writer of this guide AND the theme system, I still don't really get what each property does. But how do you see the theme in action?

## Step 4 - That file you saw in the README...

Remember the configuration file's path? Crack that open. Reminder that it's only there after opening the app for the first time. Also tell your users to do this step, because it's how you use the theme! Inside the file, you will find key-value pairs. Look for the one that says `theme`. See the value after it that says `light`? Change that to `mytheme`. The value you set is whatever you named the CSS file minus the `.css` part.

## Step 5 - Back to #3!!!!!!!!

As you (should have) saw, the colors have changed to the ones you set! Your next step (if you're not finished) is to... GO BACK TO #3!!! :)

## Step 6 - Distribute your theme...

Upload that `mytheme.css` file somewhere, it doesn't really matter. Be sure to leave instructions on placing it in the `themes` directory and using it!
