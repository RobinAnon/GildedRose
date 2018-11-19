## Before the modification

1. Before modifying the UpdateQuality method, I write tests to check if there is no bug with this method :
	- does an inventory update decrease the quality and the sell in by 1 ?
	- does the quality degrades twice as fast when the date has passed ?
	- can the quality be < 0 ?
	- can the quality be > 50 ?
	- does the sulfuras quality change ?
	- does the backstage passes quality increase gradually ?
2. We run all tests.
3. All tests have been passed -> now we can modify the UpdateQuality method.


## After the modification

1. We run again all tests.
2. All tests have been passed -> we didn't introduce any bug.
3. Now we need to test the new feature of the method -> we write new tests !
	- does the conjured items quality decrease by 2 and it's sell in by 1 on update ?
	- does the conjured items quality decrease by 4 when the date has passed ?
	- can the quality be < 0 ?
4. We run these new tests.
5. All tests have been passed -> there is no obvious bug. 
