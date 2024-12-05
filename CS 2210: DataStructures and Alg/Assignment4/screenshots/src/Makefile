CC = gcc
CFLAGS = -Wall -g
DEPS = reminder.h linked_list.h interact.h
OBJ = main.o reminder.o linked_list.o interact.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

reminder: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

.PHONY: clean

clean:
	rm -f *.o reminder 