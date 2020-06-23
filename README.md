1/ Download "Tuner.jar" and folder "images" into one location;
2/ Via cmd tape command "java -jar Tuner.jar"


# Underlying theory

This project is based on the **Discrete Fourier Transform**(DFT) result analysis. Let <img src="https://render.githubusercontent.com/render/math?math=x_1, \dots, x_N"> be consequent microphone signal measurments separated by time intervals of <img src="https://render.githubusercontent.com/render/math?math=T_s = \frac{1}{N}">. The DFT is then given by: 

<img src="https://render.githubusercontent.com/render/math?math=\LARGE X[\omega] = \sum_{k=1}^N x_k e^{-i\omega k T_s},">

where <img src="https://render.githubusercontent.com/render/math?math=\omega"> is angular frequency and <img src="https://render.githubusercontent.com/render/math?math=\omega = 2\pi f">, where <img src="https://render.githubusercontent.com/render/math?math=f"> is ordinary frequency in Hz. Now we can rewrite the above definition as follows:

<img src="https://render.githubusercontent.com/render/math?math=\LARGE X[\hat{\omega}] =\sum_{k=1}^N x_k e^{-i\hat{\omega}k}= \sum_{k=1}^N x_k e^{-i\2\pi \frac{k}{N}f},"> with <img src="https://render.githubusercontent.com/render/math?math=\hat{\omega}:=\omega T_s">.

As k's are integer, <img src="https://render.githubusercontent.com/render/math?math=X[\hat{\omega}]"> is periodic with period <img src="https://render.githubusercontent.com/render/math?math=2\pi">. Moreover for real signal x  we have: <img src="https://render.githubusercontent.com/render/math?math=X[2\pi - \hat{\omega}] = X[\hat{\omega}]^*">(conjugate). For this reason X will be defined by it's values on <img src="https://render.githubusercontent.com/render/math?math=[0,\pi]">. But what if <img src="https://render.githubusercontent.com/render/math?math=\hat{\omega} > \pi"> ? Here we refer to the [_Nyquist–Shannon–Kotelnikov theorem_](https://en.wikipedia.org/wiki/Nyquist%E2%80%93Shannon_sampling_theorem "Wikipedia"): __If a function x contains no frequencies higher than B hertz, it is completely determined by giving its ordinates at a series of points spaced <img src="https://render.githubusercontent.com/render/math?math=\frac{1}{2B}"> seconds apart.



<img src="https://render.githubusercontent.com/render/math?math=e^{i \pi} = -1">
<img src="https://render.githubusercontent.com/render/math?math=e^{i \pi} = -1">
<img src="https://render.githubusercontent.com/render/math?math=e^{i \pi} = -1">
<img src="https://render.githubusercontent.com/render/math?math=e^{i \pi} = -1">
