# Implmentation of Liar Dice game using Distributed System 

* Team Members: Jiacheng Xu, Jiangnan Yao, Shuyue Zhou

## Structure of the whole program and functions of each class

### Lobby class
Communication to each player independently. Always in (receive -> send) fashion. So only need receive-thread. After receving request from player, first check the type of request. If it is (join game type), then check the current available room. (Nee to use synchronzie because one could enter the room as others leaving at the time) After finding the room, send room number and play order number message to player. When the room is full or have enough players, sending confirmation to players. If the lobby class receives leaving game request, then it updates its global room list content.

### Player class
Player class has two stages. First is the communication to lobby class. After receving the confirmation to play, it can safely start the multicast_send thread and multicast_receive thread. The game ends when all non-faulty players reach a consensus that the game is finished and who is the winner. After the game result, player can choose to play again or quit. 



