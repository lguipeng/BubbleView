# BubbleView

##Screenshot
![screenshot](https://github.com/lguipeng/BubbleView/blob/master/screenshot/screenshot.png)

##Import to your project
###Gradle
```groovy
dependencies {
    compile 'com.lguipeng.bubbleview:library:1.0.0'
}
```
###Or
Copy [`BubbleDrawable.java`](https://github.com/lguipeng/BubbleView/blob/master/library/src/main/java/com/github/library/bubbleview/BubbleDrawable.java) [`BubbleImageView.java`](https://github.com/lguipeng/BubbleView/blob/master/library/src/main/java/com/github/library/bubbleview/BubbleImageView.java)  [`BubbleTextVew.java`](https://github.com/lguipeng/BubbleView/blob/master/library/src/main/java/com/github/library/bubbleview/BubbleTextVew.java) [`BubbleLinearLayout.java`](https://github.com/lguipeng/BubbleView/blob/master/library/src/main/java/com/github/library/bubbleview/BubbleLinearLayout.java) into your project.

##Usage
```
<com.github.library.bubbleview.BubbleTextVew
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello BubbleTextVew"
        android:padding="10dp"
        android:textColor="@android:color/white"
        app:arrowWidth="8dp"
        app:angle="8dp"
        app:arrowHeight="10dp"
        app:arrowPosition="14dp"
        app:arrowLocation="right"
        app:bubbleColor="#7EC0EE"/>
```
`app:arrowLocation` support `left` `right` `top` `bottom`

`app:angle` is the radius of bubble view

###You can get more info from [activity_main.xml](https://github.com/lguipeng/BubbleView/blob/master/app/src/main/res/layout/activity_main.xml)
