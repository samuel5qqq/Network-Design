1. Create software that is capable of doing the following:
 As input, it receives the number of nodes (N), the trac demand
values (bij) between pairs of nodes, and the unit cost values for
the potential links (aij).
 As output, the program generates a network topology, with capac-
ities assigned to the links, according to the studied model, using
the shortest path based fast solution method (see at the
end of the referred lecture note). The program also computes the
total cost of the designed network.
Important notes:
 Any programming language under any operating system can be
used, it is your choice.
 For the shortest path algorithm you may download and utilize any
existing software module from the Internet. If you use this oppor-
tunity, then include in your documentation a precise reference that
tells where the module comes from.
2. Clearly explain how your program works. It is helpful to use 
owcharts
for visualizing the explanation.
3. Run your program on randomly generated examples, as explained be-
low.
 Let the number of nodes be N = 21 in each example.
 For each example, generate the aij ; bij values according to the rules
described below. In these rules k is a parameter that will change
in the experiments.
1
{ For generating the bij values, pick independent random inte-
gers from the range [0; 1; 2; 3].
{ For generating the aij values, do the following. For any given
i, pick k random indices j1; j2; : : : ; jk, all dierent from each
other and from i. Then set
aij1 = aij2 = : : : = aijk = 1;
and set aij = 250, whenever j 6= j1; : : : ; jk. Carry out this
independently for every i.
Remark: The eect of this is that for every node i there will
be k low cost links going out of the node, the others will
have large cost. The shortest path algorithm will try to avoid
the high cost links, so it eectively means that we limit the
number of links that go out of the node, thus limiting the
network density.
 Run your program with k = 3; 4; 5 : : : ; 15. For each run generate
new random aij ; bij parameters independently.
4. Show graphically in diagrams the following:
 How does the total cost of the network depends on k?
 How does the density of the obtained network depends on k? Here
the density is dened as the number of directed edges that are
assigned nonzero capacity, divided by the total possible number
of directed edges, which is N(N 􀀀 1).
 Show some of the obtained network topologies graphically. Specif-
ically, draw three of them: one with k = 3, one with k = 8, and
one with k = 15.
