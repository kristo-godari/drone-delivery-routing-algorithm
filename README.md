# Drone routing algorithm for package delivery
## Overview
The Drone Delivery Routing Algorithm is a Java-based project designed to find the fastest drone for delivering orders 
in a drone delivery system. It utilizes a weighted graph and a shortest path algorithm to determine the optimal drone for a given order.

If you are interested only in the algorithm you can find it [here](src/main/java/org/godari/dronedelivery/services/algorithm/ShortestPathAlgorithmService.java)

## Problem statement 
Given a fleet of drones, a list of customer orders, and availability of the individual products in warehouses,
schedule the drone operations so that the orders are completed as soon as possible.
Orders can have multiple products, and products can be found in one or more warehouses.

## My Solution
My approach for solving this problem is this:
- All new customers orders will stay in a queue, we will call it **orders_queue**
- Each drone will have it's own queue ex: **drone_1_queue**, **drone_2_queue** ...
- A worker will process the **orders_queue** and will distribute orders to drone queues

This worker must know which drone offers the best time for a specific parcel, in order to distribute orders effectively. 
**In this project you will find the algorithm for making that decision.**

I chose to solve this problem with the help of graphs, and shortest path algorithms.

Let's see an example: We have 3 drones(D1, D2, D3), 3 warehouses(W1, W2, W3) and 3 clients(C1, C2, C3). 
All three drones are in the process of delivering parcels to their clients.
Drone D1 is delivering to C1, D2 to C2 and D3 to C3. Now a new order comes up for client 4 (C4), this order has tree products 
and these products can be found: Product 1 in W1, Product 2 in W2 and Product 3 in W3. 
The drone that will deliver this order must pass through all three warehouses to pick the products and then go for delivery.

With this data we can build an example graph represented below:

![Drone delivery graph](/docs/graph.png?raw=true "Drone delivery graph")

This is a directed graph with edge weights. I have selected the edge weights randomly for the sake of the argument.
Each edge between two nodes represents the time in minutes that a drone 
needs to fly form first node that has an location(latitude, longitude) to second node location(latitude, longitude).

**The question is: Which drone D1, D2, D3 offers best time for delivering the new order arrived for C4?** 

My algorithm tries all combinations to find the best route. 
For the given example the Drone D2 is the winner with the route: D2->C2->W2->W3->W1->C4 with a distance of 14. 

To calculate the number of combination that the algorithm tries we use the formula: **W! * C**, number of warehouses factorial 
multiplied by the number of clients.

For our example that is: (3!*4) = 24 combinations.
For a graph with 10 warehouses, 100 clients = (10!*100)= 362,880,000 combinations.

## Performance
The number of warehouses is very important because it grows the number of combinations exponentially. From my tests:

**Testing results for device:** Laptop ASUS Gaming 17.3" , Intel® Core™ i7-6700HQ (6M Cache, up to 3.50 GHz), 16GB DDR4 RAM.

- 100 clients, 100 drones and 10 warehouses = 77 sec
- 100 clients, 100 drones and 9 warehouses = 8 sec
- 100 clients, 100 drones and 8 warehouses = 951 ms
- 100 clients, 100 drones and 7 warehouses = 265 ms
- 100 clients, 100 drones and 6 warehouses = 68 ms
- 100 clients, 100 drones and 5 warehouses = 29 ms
- ...
- 100 clients, 100 drones and 1 warehouses = 1 ms

**Testing results for device:** Laptop MacBook Pro , Chip Apple M1 Pro, 16 GB RAM

- 100 clients, 100 drones and 10 warehouses = 53 sec
- 100 clients, 100 drones and 9 warehouses = 5 sec
- 100 clients, 100 drones and 8 warehouses = 744 ms
- 100 clients, 100 drones and 7 warehouses = 174 ms
- 100 clients, 100 drones and 6 warehouses = 63 ms
- 100 clients, 100 drones and 5 warehouses = 14 ms
- ...
- 100 clients, 100 drones and 1 warehouses = 1 ms


If you have more than 10 warehouses (is mandatory to pass throw all 11,12,13 ... warehouses) this algorithm becomes very slow and is not practical to use
because the number of combinations to test is huge. Ex: for 11 warehouses there are 3,991,680,000 combinations.
But in 99% of the cases you will have less then 10 mandatory warehouses so the algorithms performs fine.


## Contributing
If you would like to contribute to this project, please follow the standard GitHub Fork and Pull Request workflow. 
I welcome any improvements, bug fixes, or additional features that enhance the algorithm's functionality.

## License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENCE) file for details.