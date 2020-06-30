# Introduction

The goal of the project is to build a Guitar tuner that allows to tune a guitar: i.e. recognize the closest note to the sounding one and say how much and how the string tension should be corrected. 

# Underlying theory

## Fourier Transform

This project is based on the **Discrete Fourier Transform**(DFT) result analysis. Let <img src="https://render.githubusercontent.com/render/math?math=x_1, \dots, x_N"> be consequent microphone signal measurments separated by time intervals of <img src="https://render.githubusercontent.com/render/math?math=T_s = \frac{1}{N}">. The DFT is then given by: 

<img src="https://render.githubusercontent.com/render/math?math=\LARGE X[\omega] = \sum_{k=1}^N x_k e^{-i\omega k T_s},">

where <img src="https://render.githubusercontent.com/render/math?math=\omega"> is angular frequency and <img src="https://render.githubusercontent.com/render/math?math=\omega = 2\pi f">, where <img src="https://render.githubusercontent.com/render/math?math=f"> is ordinary frequency in Hz. Now we can rewrite the above definition as follows:

<img src="https://render.githubusercontent.com/render/math?math=\LARGE X[\hat{\omega}] =\sum_{k=1}^N x_k e^{-i\hat{\omega}k}= \sum_{k=1}^N x_k e^{-i\2\pi \frac{k}{N}f},"> with <img src="https://render.githubusercontent.com/render/math?math=\hat{\omega}:=\omega T_s">.

As k's are integer, <img src="https://render.githubusercontent.com/render/math?math=X[\hat{\omega}]"> is periodic with period <img src="https://render.githubusercontent.com/render/math?math=2\pi">. Moreover for real signal x  we have: <img src="https://render.githubusercontent.com/render/math?math=X[2\pi - \hat{\omega}] = X[\hat{\omega}]^*">(conjugate). For this reason X will be defined by it's values on <img src="https://render.githubusercontent.com/render/math?math=[0,\pi]"> and  the amplitude spectrum function of X (i.e. <img src="https://render.githubusercontent.com/render/math?math=|X[\hat{\omega}]|=\sqrt{Re(X[\hat{\omega}])^2 %2B Im(X[\hat{\omega}])^2}">) is symmetric on <img src="https://render.githubusercontent.com/render/math?math=[0,2\pi]"> w.r.t <img src="https://render.githubusercontent.com/render/math?math=\pi">. But what if <img src="https://render.githubusercontent.com/render/math?math=\hat{\omega} > \pi"> ? Here we refer to the [_Nyquist–Shannon–Kotelnikov theorem_](https://en.wikipedia.org/wiki/Nyquist%E2%80%93Shannon_sampling_theorem "Wikipedia"): __If a function x contains no frequencies higher than B hertz, it is completely determined by giving its ordinates at a series of points spaced <img src="https://render.githubusercontent.com/render/math?math=\frac{1}{2B}"> seconds apart__. So if we choose <img src="https://render.githubusercontent.com/render/math?math=T_s"> small enough, we guarantee that <img src="https://render.githubusercontent.com/render/math?math=\hat{\omega} < \pi">.

Function [_DoubleFFT_1D.realForward()_](https://wendykierp.github.io/JTransforms/apidocs/org/jtransforms/fft/DoubleFFT_1D.html "JTransforms doc") is used in One.class.

## Overtones

In the real life a sounding string does not produce a single frequency. It turns out that a range of frequencies is produced. Those frequencies are usually represented by the main one and it's multiples([_Overtones_](https://en.wikipedia.org/wiki/Overtone#:~:text=An%20overtone%20is%20any%20frequency,overtones%20together%20are%20called%20partials. "Overtone")). To undertand why this happens it's useful to look at the
[_solution to the Vibrating String differential equation_](https://tutorial.math.lamar.edu/classes/de/VibratingString.aspx "PDE Solution"). According to it, any string movement may be decomposed into an infinite sum of harmonics. Each harmonic describes a sinusoidal shape of oscillating string i.e. oscillation amplitude of each point of the string. That's why there are several frequencies in the signal: the main one(corresponding to the vibrating part of the string) and **overtones**. 

# Recognition algorithm.

- Perform DFT and calculate **spectrogram**;
- Calculate the **spectrogram average** <img src="https://render.githubusercontent.com/render/math?math=S_{avg}"> for frequencies from 20Hz to _max_freq_(48K Hz, 22K Hz, 8K Hz or 4K Hz - depends on the user's choice);
- Calculate <img src="https://render.githubusercontent.com/render/math?math=BS_{avg}"> - the average of the above spectrogram points **where value is below** <img src="https://render.githubusercontent.com/render/math?math=S_{avg}">, so that <img src="https://render.githubusercontent.com/render/math?math=BS_{avg} < S_{avg}">; define threshold <img src="https://render.githubusercontent.com/render/math?math=T := 4 * BS_{avg}">;
- For each frequency <img src="https://render.githubusercontent.com/render/math?math=f"> in the spectrum calculate it's weight <img src="https://render.githubusercontent.com/render/math?math=w_f">: the sum over spectrum values for <img src="https://render.githubusercontent.com/render/math?math=f, 2f, 3f, 4f \dots"> up to _max_freq_; it's important to note that we add up only values above <img src="https://render.githubusercontent.com/render/math?math=T">; we also account for the fact that note frequencies are rounded and the spectrum peaks are not perfect delta-functions, so for <img src="https://render.githubusercontent.com/render/math?math=w_f"> we add up spectrum value for <img src="https://render.githubusercontent.com/render/math?math=f"> and values for <img src="https://render.githubusercontent.com/render/math?math=f - 1"> and <img src="https://render.githubusercontent.com/render/math?math=f %2B 1"> weighted by 0.5;
- Choose <img src="https://render.githubusercontent.com/render/math?math=f_{max}"> with maximal <img src="https://render.githubusercontent.com/render/math?math=w_f">;
- For each multiple <img src="https://render.githubusercontent.com/render/math?math=f"> of <img src="https://render.githubusercontent.com/render/math?math=f_{max}"> check whether <img src="https://render.githubusercontent.com/render/math?math=w_f"> is at least 80% of <img src="https://render.githubusercontent.com/render/math?math=w_{f_{max}}">. if it is, replace <img src="https://render.githubusercontent.com/render/math?math=f_{max}"> with such maximal possible value of <img src="https://render.githubusercontent.com/render/math?math=f"> satisfying this property; (This is a __robusness measure__. Sometimes higher frequencies force __subtones__(e.g. in EADGBE guitar standard the 6th E note vibrations may force the 1st E note to vibrate too because of the resonance))
- Identify the closest to <img src="https://render.githubusercontent.com/render/math?math=f_{max}"> note and identify the distance between them;
- There is also a __sensitivity filter__: the higher it is, the larger <img src="https://render.githubusercontent.com/render/math?math=f_{max}"> should be for a note to be accepted/recognized;
- There is a __recognition mode__ designed to translate a melody(a sequence of notes) into tabs; in this mode it's necessary to separate a _new note_ from the _previous_ one.

# Project structure
- __Analyzer.java__ contains the core implementation of the algorithm described above;
- __ITable.java__ contains the GUI implementation and _main()_ function;
- __LocArray.java__ describes the note localization (notes - tabs translation);
- __MinimalStaticChart.java__ draws the Spectrogram plot;
- __One.java__ reads the raw data from buffer and performs DFT on it;
- __NoteStruct1.java__, __NoteStruct2.java__, __Note.java__ and __Location.java__ define auxiliary classes for notes storage with minimal functionality;

# How to launch

- Put "Tuner.jar" and "images" folder into the same location _Your_location_;
- From _Your_location_ type command:

```
java -jar Tuner.jar
```
