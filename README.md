# Implmentation of Liar Dice game using Distributed System 

* Team Members: Jiacheng Xu, Jiangnan Yao, Shuyue Zhou

## Structure of the whole program and functions of each class

Lobby class: Communication to each player independently. Always in (receive -> send) fashion. So only need receive-thread. After receving request from player 