# Drone delivery routing algorithm
##### Problem statement: 
Given a fleet of drones, a list of customer orders, and availability of the individual products in warehouses,
schedule the drone operations so that the orders are completed as soon as possible.
Orders can have multiple products, and products can be found in one or more warehouses.

My approach for solving this problem is this:
- All customers orders will stay in a queue, we will call it **orders_queue**
- Each drone will have it's own queue ex: **drone_1_queue**, **drone_2_queue** ...
- A worker will process the **orders_queue** and will distribute orders to drones queues

This worker must know which drone offers the best time for a specific parcel, in order to distribute orders effectively. 
I chose to solve this problem with the help of graphs, and shortest path algorithms.

Let's see an example: We have 3 drones(D1, D2, D3), 3 warehouses(W1, W2, W3) and 3 clients(C1, C2, C3). 
All three drones are in the process of delivering parcels to their clients.
Drone D1 is delivering to C1, D2 to C2 and D3 to C3. Now a new order shows for client 4 (C4), this order has tree products 
and these products can be found: Product 1 in W1, Product 2 in W2 and Product 3 in W3. 
The drone that will deliver this order must pass through all three warehouses to pick the products and then go for delivery.

With this data we can build the an example graph represented below:

![Drone delivery graph](/docs/graph.png?raw=true "Drone delivery graph")

This is an directed graph with edge weights. I have selected the edge weights randomly for the sake of the argument.
Each edge between two nodes represents the time in minutes that a drone 
needs to fly form first node that has an address(latitude, longitude) to second node address(latitude, longitude).

The question is: Which drone D1, D2, D3 offers best time for delivering the new order arrived for C4? 
My algorithm tries all combinations to find the best route. 

For the given example the Drone D2 is the winner with the route: D2->C2->W2->W3->W1->C4 with a distance of 14. 

To calculate the number of combination that the algorithm tries we use the formula: W! * C, number of warehouses factorial 
multiplied by the number of clients.

For our example that's is: (3!*4) = 24 combinations.
For and graph with 10 warehouses, 100 clients = (10!*100)= 362,880,000 combinations.

**Performance**:
The number of warehouses is very important because it grows the number of combinations. From my tests:
- 100 clients, 100 drones and 10 warehouses = 77 sec
- 100 clients, 100 drones and 9 warehouses = 8 sec
- 100 clients, 100 drones and 8 warehouses = 951 ms
- 100 clients, 100 drones and 7 warehouses = 265 ms
- 100 clients, 100 drones and 6 warehouses = 68 ms
- 100 clients, 100 drones and 5 warehouses = 29 ms
...
- 100 clients, 100 drones and 1 warehouses = 1 ms

If you have more than 10 warehouses (is mandatory to pass throw all 11,12,13 ... warehouses) this algorithm becomes very slow and is not practical to use
because the number of combinations to test is huge. Ex: for 11 warehouses there are 3,991,680,000 combinations.
But in 95% of the cases you will have less then 10 mandatory warehouses so the algorithms performs fine.

I did the tests using an:	Notebook / Laptop ASUS Gaming 17.3" ROG GL752VW, FHD, Intel® Core™ i7-6700HQ (6M Cache, up to 3.50 GHz), 16GB DDR4, 1TB 7200RPM + 128GB SSD, GeForce GTX 960M 4GB