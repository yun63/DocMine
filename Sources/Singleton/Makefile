
CXX := g++

%.o: %.cpp
	@echo "-- Building $@"
	$(CXX) -g -std=c++11 -c $< -o $@

%.o: %.cc
	@echo "-- Building $@"
	$(CXX) -g -std=c++11 -c $< -o $@

all: Singleton


Singleton: main.cpp
	$(CXX) -g -std=c++11 -o $@ $^ -lpthread
	

.PHONY: clean
clean:
	@rm -f *.core *.o
	@rm -f Singleton
	@rm -rf *.dSYM
