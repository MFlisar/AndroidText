### Text [![Release](https://jitpack.io/v/MFlisar/AndroidText.svg)](https://jitpack.io/#MFlisar/AndroidText)

**Text** is a very simple sealed class to help handling texts that can be either a string resource (`Int`) or a simply text (`String`)

### Features

* contains one class only
* has a few lines of code only
* contains extension functions for `Int` and `String`

### Gradle (via JitPack.io)

1) Add jitpack to your project's build.gradle:
```
repositories {
	maven { url "https://jitpack.io" }
}
```

2) Add the compile statement to your module's build.gradle:
```
dependencies {
	implementation 'com.github.MFlisar:AndroidText:<LATEST-VERSION>'
}
```

### Example

```
val text1 = "Test".toText()
val text2 = R.string.text.toText()
val text3 = Text.Empty
val text4 = SpannableString("").toText() // works with any class that implements CharSequence

// display in TextView/Toolbar/Button/Any
text1.display(textView)
text1.display(view.context) {
	view.text = it
}

// get string as CharSequence
val string1 = text1.get(context)
val string2 = text2.get(context)
val string3 = text3.get(context)
val string4 = text4.get(context)

// check if text is empty
val empty = text1.isEmpty()