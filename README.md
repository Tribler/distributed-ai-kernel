# Distributed-AI-kernel
Basic implementation of Gossip Learning (machine learning over fully distributed data), for the case of classification with linear models.



The project is a small demo implementation of Robert Ormandi and Istvan Heged's  [scientific paper](https://arxiv.org/abs/1109.1396).



## Getting Started

```
git clone https://github.com/Tribler/distributed-ai-kernel.git
```


We recommend to have Maven installed on your local machine:
```
sudo apt update
```
```
sudo apt install maven
```
Finally check the installation 
```
mvn -version
```



### Running
```
mvn exec:java 
```
```
OUR_AVERAGE SLOPE: 0.7034892602092983  OUR AVERAGE INTERCEPT: 0.6037076408018176
[BEST_FIT LINE]: SLOPE:0.7142857142857143 intercept: 0.6071428571428572
BEST FIT LINE error:1.6071428571428577
OUR LINE error:1.6139083467505504
RELATIVE ERROR: 0.995808008787293
```


![Convergence over 1000 iterations](https://lh3.googleusercontent.com/3M9Wf_7ZYFx2uYjecslzdYAc__YcTZueZBSkgrGUPd-UhNj0zHnP3LzKOEnYDlT_R8r5aE0Dr_pQ)










## Running the tests
```
mvn test
```


## Authors

* **JayDew** - *Initial work* - [GitHub]([https://github.com/JayDew](https://github.com/JayDew))
* **Matei Enache** - *Initial work* - [GitHub]([[https://github.com/mateicristea88](https://github.com/mateicristea88)])
* **Johan Pouwelse** - *Coordonator* - [GitHub]([[https://github.com/synctext](https://github.com/synctext)])


## License

This project is licensed under the MIT License


## Acknowledgments

* Hat tip to anyone whose code was used
* The project is still in early days of development
