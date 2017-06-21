# Drone delivery routing algorithm
##### Problem statement: 
Given a fleet of drones, a list of customer orders and availability of the individual products in warehouses,
schedule the drone operations so that the orders are completed as soon as possible.
Orders can have multiple products, and products can be found in one or more warehouses.

My approach for solving this problem is this:
- All customers orders will stay in a queue, we will call it **orders_queue**
- Each drone will have it's own queue ex: **drone_1_queue**, **drone_2_queue** ...
- A processor will process the **orders_queue** and will distribute orders to drones queues

This processor must know which drone offers the best time for a specific parcel, in order to distribuite orders effectively. I chose to solve this problem with the help of graphs.

Let's see an example: We have 3 drones(D1, D2, D3), 3 warehouses(W1, W2, W3) and 3 clients(C1, C2, C3). All three drones are in the process of delivering parcels to their clients.
Drone D1 is delivering to C1, D2 to C2 and D3 to C3. Now a new order shows for client 4 (C4), this order has tree products 
and these products can be found: Product 1 in W1, Product 2 in W2 and Product 3 in W3. The drone that will deliver this order must pass throug all three warehouses.

With this data we can build the graph represented below:

![Drone delivery graph](/docs/graph.png?raw=true "Drone delivery graph")

This is an directed graph with edge weights. Each edge between two nodes represents the time that a drone 
needs to fly form first node address(latitude, longitude) to second node address(latitude, longitude).

The question is: Which drone D1, D2, D3 offers best time to C4? 
My algorithm tries all combinations to find the best route. Example output for a graph with 7 warehouses, 100 clients and 100 drones: C0->W5->W1->W7->W3->W2->W4->W6->C23->D23->34.0 where the 34.0 is the distance of the best route.

**Performance**:
The number of warehouses is very important because it grows the number of combinations. From my tests:
- 100 clients, 100 drones and 10 warehouses = 24 sec
- 100 clients, 100 drones and 9 warehouses = 12 sec
- 100 clients, 100 drones and 8 warehouses = 1 sec
- 100 clients, 100 drones and 7 warehouses = 0 sec
- 100 clients, 100 drones and 6 warehouses = 0 sec
...
- 100 clients, 100 drones and 1 warehouses = 0 sec

If you have more than 10 warehouses (is mandatory to pass throw all 11,12,13 ... warehouses) this algorithm becames very slow and is not practical to use
because the number of combinations to test is huge. 
But in 95% of the cases  you will have less then 5 mandatory warehouses so this performs fine.

I did the tests using an:	Notebook / Laptop ASUS Gaming 17.3" ROG GL752VW, FHD, Intel® Core™ i7-6700HQ (6M Cache, up to 3.50 GHz), 16GB DDR4, 1TB 7200RPM + 128GB SSD, GeForce GTX 960M 4GB