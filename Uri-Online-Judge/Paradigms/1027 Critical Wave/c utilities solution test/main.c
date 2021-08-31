#ifdef _MSC_VER
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreserved-id-macro"
#define _CRT_SECURE_NO_WARNINGS
#define _CRTDBG_MAP_ALLOC
#pragma clang diagnostic pop
#include <stdlib.h>
#include <crtdbg.h>
#endif

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdint.h>
#include <limits.h>

//UTILITIES


//function alias
typedef int (*Comparator)(void*, void*);
typedef void (*Destructor)(void*);


//assertions module
extern void (*assertCallback)(const char*, const char*);

#define ASSERTION_PATTERN(condition, message)\
	if(!(condition)){\
		if(assertCallback) assertCallback( __func__, (message));\
		exit(EXIT_FAILURE);\
	}

#define ASSERT_CONDITION(condition)\
	ASSERTION_PATTERN(condition, #condition)	

#define ASSERT_ARGUMENT(condition)\
	ASSERTION_PATTERN(condition, "Invalid argument value '" #condition "'")

#define ASSERT_NULL_ARGUMENT(condition)\
	ASSERTION_PATTERN(condition, "Invalid null argument '" #condition "'")

#define ASSERT_ALLOCATION(condition)\
	ASSERTION_PATTERN(condition, "Out of memory")

#define ASSERT_HASH(condition)\
	ASSERTION_PATTERN(condition, "Invalid hash")

#define ASSERT_OUT_OF_BOUNDS(condition)\
	ASSERTION_PATTERN(condition, "Out of bounds")

//LIST

typedef struct List List;

List* listCreate(Destructor dtor);
void listDestroy(void* s);
uint32_t listSize(List* self);
bool listEmpty(List* self);
void* listFirst(List* self);
void* listLast(List* self) ;
void* listPeek(List* self);
bool listContains(List* self, void* value);
void* listBegin(List* self);
void* listNext(List* self);
void listPush(List* self, void* value);
void listEnqueue(List* self, void* value);
bool listRemove(List* self, void* value);
bool listPop(List* self);
bool listDequeue(List* self);
void listClean(List* self);



//WRAPPERS.H
typedef struct Integer Integer;
typedef struct Double Double;
typedef struct Boolean Boolean;
typedef struct Character Character;


//integer methods
Integer* intCreate(int value);
void intDestroy(void* s);
int32_t intGet(Integer* self);
void intSet(Integer* self, int value);
void intAdd(Integer* self, int value);
int intCompare(void* s, void* o);


//double methods
Double* doubleCreate(double value);
void doubleDestroy(void* s);
double doubleGet(Double* self);
void doubleSet(Double* self, double value);
int doubleCompare(void* s, void* o);

//ARRAY.H

typedef struct Array Array;

Array* arrayCreate(Destructor dtor);
void arrayDestroy(void* self);	
uint32_t arraySize(Array* self);
uint32_t  arrayCapacity(Array* self);
void* arrayAt(Array* self, uint32_t index);
void* arrayLast(Array* self);
void arrayAdd(Array* self, void* value);
void arrayInsert(Array* self, uint32_t index, void* value);
void arrayRemove(Array* self, uint32_t index);
bool arrayRemoveValue(Array* self, void* value);
uint32_t arrayIndexOf(Array* self, void* value);
void arrayReserve(Array* self, uint32_t capacity);
void arrayClean(Array* self);
void arraySort(Array* self, Comparator comp);


//IMAP

typedef struct Imap Imap;
Imap* imapCreate(Destructor dtor);
void imapDestroy(void* s);
uint32_t imapSize(Imap* self);
uint32_t imapCapacity(Imap* self);
void* imapGet(Imap* self, int64_t key);
void* imapAt(Imap* self, uint32_t index);
void imapPut(Imap* self, int64_t key, void* value);
void imapReserve(Imap* self, uint32_t capacity);
void imapRemove(Imap* self, int64_t key);
void imapRemoveAt(Imap* self, uint32_t index);
void imapClean(Imap* self);

//UTILITIES.C
//assertions module
static void assertDefaultCallback(const char* fn, const char* message) {
	fprintf(stderr, "In %s(). Runtime error: %s.", fn, message);
}

void (*assertCallback)(const char*, const char*) = assertDefaultCallback;


//wrappers.c

//structs
struct Integer {
	uint32_t hash;
	int32_t value;
};

struct Double {
	uint32_t hash;
	double value;
};

//hashs
static const uint32_t INTEGER_HASH = 2008563;
static const uint32_t DOUBLE_HASH = 2929215232;


//integer methods
Integer* intCreate(int value) {
	Integer* self = malloc(sizeof(Integer));
	ASSERT_ALLOCATION(self)
	self->hash = INTEGER_HASH;
	self->value = value;
	return self;
}

void intDestroy(void* s) {
	Integer* self = s;
	if (s) {
		ASSERT_HASH(self->hash == INTEGER_HASH)
		free(self);
	}
}

int32_t intGet(Integer* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == INTEGER_HASH)
	return self->value;
}

void intSet(Integer* self, int value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == INTEGER_HASH)
	self->value = value;
}

void intAdd(Integer* self, int value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == INTEGER_HASH)
	self->value+= value;
}

int intCompare(void* s, void* o) {
	if (s && o) {
		return ((Integer*)s)->value - ((Integer*)o)->value;
	}
	if (s) {
		return 1;
	}
	return -1;	
}


//double methods
Double* doubleCreate(double value) {
	Double* self = malloc(sizeof(Double));
	ASSERT_ALLOCATION(self)
	self->hash = DOUBLE_HASH;
	self->value = value;
	return self;
}

void doubleDestroy(void* s) {
	Double* self = s;
	if (s) {
		ASSERT_HASH(self->hash == DOUBLE_HASH)
		free(self);
	}
}

double doubleGet(Double* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == DOUBLE_HASH)
	return self->value;
}

void doubleSet(Double* self, double value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == DOUBLE_HASH)
	self->value = value;
}

int doubleCompare(void* s, void* o) {
	if (s && o) {
		if(((Double*)s)->value > ((Double*)o)->value){
			return 1;
		}
		if (((Double*) s)->value < ((Double*) o)->value) {
			return -1;
		}
		return 0;
	}
	if (s) {
		return 1;
	}
	return -1;	
}





//ARRAY.C
struct Array {   
	uint32_t hash;
    void** entries;
    Destructor dtor;
    uint32_t size;
    uint32_t capacity; 
};

static const uint32_t ARRAY_HASH = 215461380;

//methods
Array* arrayCreate(Destructor dtor){
	
	Array* self = malloc(sizeof(Array));	
    ASSERT_ALLOCATION(self != NULL)	
    self->entries = malloc(sizeof(void*) * 8);
    ASSERT_ALLOCATION(self->entries != NULL)    
    self->hash = ARRAY_HASH;
    self->dtor = dtor;    
    self->capacity = 8;    
    self->size = 0;
    return self;
}

//...methods
void arrayDestroy(void* s) {
    Array* self = s;
    if (self) {
        ASSERT_HASH(self->hash == ARRAY_HASH)
        if (self->dtor) {
            for (uint32_t i = 0; i < self->size; i++) {
                self->dtor(self->entries[i]);
            }
        }
        free(self->entries);
        free(self);
    }    
}


uint32_t arraySize(Array* self){
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
	return self->size;
}

uint32_t arrayCapacity(Array* self){
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
	return self->capacity;
}

void* arrayAt(Array* self, uint32_t index) {
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    ASSERT_OUT_OF_BOUNDS(index < self->size)
    return self->entries[index];
}

void* arrayLast(Array* self) {
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    if (self->size > 0) {
        return self->entries[self->size - 1];
    }
    return NULL;	
}

void arrayInsert(Array* self, uint32_t index, void* value) {
    
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)

    if (index >= self->size) {
        index = self->size++;
    }
    else if (self->dtor) {
        self->dtor(self->entries[index]);
    }

    if (index >= self->capacity) {
        arrayReserve(self, 2 * self->capacity);
    }
    self->entries[index] = value;    
}

void arrayAdd(Array* self, void* value){
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    self->size++;
    if (self->size >= self->capacity) {
        arrayReserve(self, 2 * self->capacity);
    }
    self->entries[self->size - 1] = value;       
}


void arrayRemove(Array* self, uint32_t index) {       
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    ASSERT_OUT_OF_BOUNDS(index < self->size)
    if (self->dtor) {
        self->dtor(self->entries[index]);
    }
    for (uint32_t i = index; i < self->size - 1; i++) {
        self->entries[i] = self->entries[i + 1];
    }
    self->size--;
}

bool arrayRemoveValue(Array* self, void* value) {  
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    uint32_t index = arrayIndexOf(self, value);
    if (index != self->size) {
        arrayRemove(self, index);
        return true;
    } else return false;
}

uint32_t arrayIndexOf(Array* self, void* value) {   
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    uint32_t index = self->size;
    for (uint32_t i = 0; i < self->size; i++) {
        if (value == arrayAt(self, i)) {
            index = i;
            break;
        }
    }
    return index;
}

void arrayReserve(Array* self, uint32_t capacity) {   
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    if (self->capacity >= capacity) return;
    self->entries = realloc(self->entries, sizeof(void*) * capacity); 
    ASSERT_ALLOCATION(self->entries)
    self->capacity = capacity;
}

void arrayClean(Array* self) {
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    if(self->size > 0 || self->capacity > 8){
        if (self->dtor) {
            for (uint32_t i = 0; i < self->size; i++) {
                self->dtor(self->entries[i]);
            }
        }
        free(self->entries);
        self->entries = malloc(sizeof(void*) * 8); 
        ASSERT_ALLOCATION(self->entries)
        self->capacity = 8;
        self->size = 0;
    }
}

void arraySort(Array* self, Comparator comp) {
    
    ASSERT_NULL_ARGUMENT(self)
    ASSERT_HASH(self->hash == ARRAY_HASH)
    
    uint32_t gap = 0;
    while(gap < self->size) gap = (gap * 3) + 1;
    gap/= 3;

    while (gap > 0) {
        for (uint32_t i = gap; i < self->size; i += 1) {

            void* temp = self->entries[i];
            uint32_t j = i;

            while(j >= gap && comp(self->entries[j - gap],  temp) > 0){
                self->entries[j] = self->entries[j - gap];
                j -= gap;
            }
            self->entries[j] = temp;
        }
        gap/= 3;
    }
}




//LIST.C
//forward struct and typedef declaration
typedef struct ListNode ListNode;

//main struct
struct List {
	uint32_t hash;
	uint32_t size;
	Destructor dtor;
	ListNode* first;
	ListNode* last;
	ListNode* previousNode;
};

//auxiliary structs
struct ListNode {
	void* value;	
	struct ListNode* next;
	struct ListNode* prev;
};

//constants and static constants
static const uint32_t LIST_HASH = 2089323073;

//static prototypes and inline functions
static inline ListNode* createNode(void* value) {
	ListNode* node = malloc(sizeof(ListNode));
	ASSERT_ALLOCATION(node)	
	node->value = value;	
	node->next = NULL;
	node->prev = NULL;
	return node;
}

static inline void destroyNode(ListNode* node, Destructor dtor) {
	if (dtor) dtor(node->value);
	free(node);
}


List* listCreate(Destructor dtor) {
	List* self = malloc(sizeof(List));	
	ASSERT_ALLOCATION(self)
	self->hash = LIST_HASH;
	self->dtor = dtor;
	self->size = 0;
	self->first = NULL;
	self->last = NULL;
	self->previousNode = NULL;	
	return self;
}

void listDestroy(void* s) {
	List* self = s;
	if (self) {
		ASSERT_HASH(self->hash = LIST_HASH)
		listClean(self);
		free(self);
	}
}

uint32_t listSize(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return self->size;
}

bool listEmpty(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return self->size == 0;
}

void* listFirst(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return self->first->value;
}

void* listLast(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return self->last->value;
}

void* listPeek(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return self->last->value;
}

bool listContains(List* self, void* value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	ListNode* node = self->first;
	while (node && node->value != value) {
		node = node->next;
	}
	return (bool) node;
}

void* listBegin(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	if (self) {
		self->previousNode = self->first;
		return self->first ? self->first->value : NULL;
	}
	return NULL;	
}

void* listNext(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	if (self->previousNode) {
		self->previousNode = self->previousNode->next;
		if (self->previousNode) {
			return self->previousNode->value;
		}		
	}
	return NULL;
}

void listPush(List* self, void* value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	ListNode* node = createNode(value);	
	if (self->last) {
		node->prev = self->last;
		self->last->next = node;		
	}
	else {
		self->first = node;		
	}	
	self->last = node;
	self->size++;
}

void listEnqueue(List* self, void* value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	ListNode* node = createNode(value);
	node->next = self->first;
	self->first->prev = node;		
	self->first = node;
	self->size++;
}

static bool listRemoveNode(List* self, ListNode* node) {
	
	if (node) {
		if (node == self->first || node == self->last) {
			if (node == self->first) {
				if(node->next) node->next->prev = NULL;
				self->first = node->next;
			}
			if (node == self->last) {
				if(node->prev) node->prev->next = NULL;
				self->last = node->prev;
			}
		}
		else {
			node->prev->next = node->next;
			node->next->prev = node->prev;
		}
		if (node == self->previousNode) {
			self->previousNode = node->prev;
		}		
		
		destroyNode(node, self->dtor);
		self->size--;
		return true;
	}
	else return false;
}

bool listRemove(List* self, void* value) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)

	//tests if the value is equal to self->nextNode->prev or self->last
	//not being equal to anyone sets node equals to self->first
	ListNode* node = self->first;
	if (self->previousNode && value == self->previousNode->value) {
		node = self->previousNode;
	} else if(self->last && self->last->value == value){
		node = self->last;
	}

	//search in crescent order
	while (node && node->value != value) {
		node = node->next;
	}

	//then returns null if not found or (if found) the return of listRemoveNode() method.
	return (bool) (node && listRemoveNode(self, node));	
}

bool listPop(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return listRemoveNode(self, self->last);
}

bool listDequeue(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	return listRemoveNode(self, self->first);
}

void listClean(List* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash = LIST_HASH)
	while (listSize(self)) {
		listRemoveNode(self, self->last);
	}
	self->size = 0;
}


//IMAP.C
#define SWAP(a, b) { void* temp = a; a = b; b = temp; }

typedef struct Uint {
	uint32_t value;
} Uint;

static inline Uint* createUint(uint32_t value) {
	Uint* self = malloc(sizeof(Uint));
	ASSERT_ALLOCATION(self)
	self->value = value;
	return self;
}

typedef struct Lint64 {
	int64_t value;
}Lint64;

static inline Lint64* createLint64(int64_t value) {
	Lint64* self = malloc(sizeof(Lint64));
	ASSERT_ALLOCATION(self)
	self->value = value;
	return self;
}

typedef struct Entry {	
	void* value;		
	Uint* reference;
} Entry;

struct Imap {
	uint32_t hash;
	uint32_t size;
	uint32_t capacity;	
	Lint64** keys;
	Destructor dtor;
	Entry* entries;
	List** table;
};

static const uint32_t IMAP_HASH = 2089219020;

static inline uint32_t calcHash(int64_t key, uint32_t max) {
	uint64_t x = (uint64_t) key;
	x = (x ^ (x >> 30)) * UINT64_C(0xbf58476d1ce4e5b9);
    x = (x ^ (x >> 27)) * UINT64_C(0x94d049bb133111eb);
    x = x ^ (x >> 31);
    return ((uint32_t) x) % max;
}

Imap* imapCreate(Destructor dtor) {
	Imap* self = malloc(sizeof(Imap));
	ASSERT_ALLOCATION(self)

	self->hash = IMAP_HASH;
	self->size = 0;
	self->capacity = 16;
	self->dtor = dtor;
	
	//create table
	self->table = malloc(self->capacity * sizeof(List*));	
	ASSERT_ALLOCATION(self->table)
	self->entries = malloc(self->capacity * sizeof(Entry));
	ASSERT_ALLOCATION(self->entries)
	self->keys = malloc(self->capacity * sizeof(Lint64*));
	ASSERT_ALLOCATION(self->keys)
	
	for (uint32_t i = 0; i < self->capacity; i++) {
		self->table[i] = listCreate(NULL);
	}
	//then return
	return self;
}

void imapDestroy(void* s) {
	Imap* self = s;
	if (self) {
		ASSERT_HASH(self->hash == IMAP_HASH)

		for (uint32_t i = 0; i < self->capacity; i++) {
			listDestroy(self->table[i]);
			if(i >= self->size){
				continue;
			}
			free(self->keys[i]);
			if(self->dtor){
				self->dtor(self->entries[i].value);
			}
			free(self->entries[i].reference);
		}

		free(self->table);
		free(self->keys);
		free(self->entries);
		free(self);
	}	
}


uint32_t imapSize(Imap* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	return self->size;
}

uint32_t imapCapacity(Imap* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	return self->capacity;
}

void* imapGet(Imap* self, int64_t key) {	
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	
	List* bucket = self->table[calcHash(key, self->capacity)];
	for (Lint64* k = listBegin(bucket); k != NULL; 
		k = listNext(bucket)) {
		Uint* reference = listNext(bucket);
		if (k->value == key) {
			return self->entries[reference->value].value;
		}
	}
	return NULL;
}

void* imapAt(Imap* self, uint32_t index) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	ASSERT_OUT_OF_BOUNDS(index < self->size)
	return self->entries[index].value;
}

void imapPut(Imap* self, int64_t key, void* value) {
	ASSERT_NULL_ARGUMENT(self)	
	ASSERT_HASH(self->hash == IMAP_HASH)
	
	if (self->size >= self->capacity){
		imapReserve(self, self->capacity * 2);
	}	

	bool contains = false;
	List* bucket = self->table[calcHash(key, self->capacity)];
	
	for (Lint64* lsKey = listBegin(bucket); lsKey != NULL; 
		lsKey = listNext(bucket))
	{		
		Uint* index = listNext(bucket);
		if (lsKey->value == key) {
			Entry* entry = &self->entries[index->value];
			if(self->dtor) self->dtor(entry->value);
			entry->value = value;			
			contains = true;				
			break;
		}
	}	

	if (!contains) {	
		//fill bucket		
		Lint64* k = createLint64(key);
		Uint* index = createUint(self->size);
		listPush(bucket, k);
		listPush(bucket, index);

		//fill entries and keys
		self->entries[self->size].value = value;				
		self->entries[self->size].reference = index;
		self->keys[self->size] = k;
				
		//change size
		self->size++;
	}
}

void imapReserve(Imap* self, uint32_t capacity) {
	
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)

	if (self->capacity >= capacity) return;

	//create replacement table
	List** table = malloc(capacity * sizeof(List*));
	ASSERT_ALLOCATION(table)
	
	for (uint32_t i = 0; i < capacity; i++) {
		table[i] = listCreate(NULL);
	}

	for (uint32_t i = 0; i < self->capacity; i++) {

		List* source = self->table[i];		
		
		for (Lint64* key = listBegin(source); key != NULL; 
			key = listNext(source)) {
			int* index = listNext(source);
			List* bucket = table[calcHash(key->value, capacity)];
			listPush(bucket, key);
			listPush(bucket, index);
		}		
	}
	
	//swap table and realloc keys and entries
	SWAP(self->table, table)	
	self->keys = realloc(self->keys, capacity * sizeof(char*));
	ASSERT_ALLOCATION(self->keys)
	self->entries = realloc(self->entries, capacity* sizeof(Entry));
	ASSERT_ALLOCATION(self->entries)
	
	//destroy previous table
	for (uint32_t i = 0; i < self->capacity; i++) {
		listDestroy(table[i]);
	}
	free(table);
	//set new capacity
	self->capacity = capacity;		
}

void imapRemove(Imap* self, int64_t key) {
	ASSERT_NULL_ARGUMENT(self)	
	ASSERT_HASH(self->hash == IMAP_HASH)
	
	Uint* index = NULL;
	bool contains = false;
	List* bucket = self->table[calcHash(key, self->capacity)];
	
	for (Lint64* k = listBegin(bucket); k != NULL; 
		k = listNext(bucket)){		
		
		index = listNext(bucket);
		if (k->value == key) {	
			contains = true;			
			listRemove(bucket, k);
			listRemove(bucket, index);
			break;
		}	
	}

	if (contains) {
		//remove value, and free key 
		if (self->dtor) {
 			self->dtor(self->entries[index->value].value);
		}

		free(self->keys[index->value]);

		//set new size
		--self->size;

		//update entries and keys
		for (uint32_t i = index->value; i < self->size; i++) {
			self->keys[i] = self->keys[i + 1];
			self->entries[i] = self->entries[i + 1];
			self->entries[i].reference->value = i;
		}

		//free index
		free(index);
	}
}

void imapRemoveAt(Imap* self, uint32_t index) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	ASSERT_OUT_OF_BOUNDS(index < self->size)
	int64_t key = self->keys[index]->value;
	imapRemove(self, key);
}

void imapClean(Imap* self) {
	ASSERT_NULL_ARGUMENT(self)
	ASSERT_HASH(self->hash == IMAP_HASH)
	
	//first create a empty imap
	Imap* empty = imapCreate(self->dtor);	
		
	//then, swap table and keys
	SWAP(self->table, empty->table)
	SWAP(self->keys, empty->keys)
	SWAP(self->entries, empty->entries)

	//Set capacity and size
	uint32_t temp = self->capacity;
	self->capacity = empty->capacity;
	empty->capacity = temp;
	empty->size = self->size;
	self->size = 0;

	//finally destroy empty
	imapDestroy(empty);
}

//MAIN.C

static int count(Array* lower, Array* higher) {    
    
    uint32_t l = 0, h = 0;
    int lCounter = 0;
    int lValue = INT_MIN;
    int lLine = 0;

    int hCounter = 0;       
    int hValue = INT_MIN;
    int hLine = 1;

    while (l < arraySize(lower) || h < arraySize(higher)) {
        int cl = l < arraySize(lower) ? intGet(arrayAt(lower, l)) : INT_MAX; 
        int ch = h < arraySize(higher) ? intGet(arrayAt(higher, h)) : INT_MAX;
        int next = 0, nextValue = 0;
        if (cl < ch) {
            next = 0;
            nextValue = cl;
            l++;
        } else {
            next = 1;
            nextValue = ch;
            h++;
        }
        
        if (next == lLine && lValue < nextValue) {
            lValue = nextValue;
            lLine = !lLine;
            lCounter++;
        }
        if (next == hLine && hValue < nextValue) {
            hValue = nextValue;
            hLine = !hLine;
            hCounter++;
        }
    }

    return hCounter > lCounter ? hCounter : lCounter;
}

static void solve(int n, Array* ans) {
        
    Imap* lines = imapCreate(arrayDestroy);
    List* keys = listCreate(intDestroy);

    //get data (n log n)
    for(int i = 0; i < n; i++){       
        int x, y;
        scanf("%d", &x);
        scanf("%d", &y);      
        Array* line = imapGet(lines, y);

        if (line == NULL) {
            line = arrayCreate(intDestroy);            
            imapPut(lines, y, line);
            listPush(keys, intCreate(y));
        }
        arrayAdd(line, intCreate(x));
    }

    for (uint32_t i = 0; i < imapSize(lines); i++) {
        arraySort(imapAt(lines, i), intCompare);    
    }
    
    int answer = 1;
    for(Integer* key = listBegin(keys); key != NULL; 
        key = listNext(keys)){
        int64_t k = intGet(key);
        Array* lower = imapGet(lines, k);
        Array* higher = imapGet(lines, k + 2);
        if (!higher) {
            continue;
        }
        int counter = count(lower, higher);
        if(counter > answer){
            answer = counter;
        }
    }

   
    arrayAdd(ans, intCreate(answer));
    imapDestroy(lines);
    listDestroy(keys);
}



int main() {

	int n = 0;
	Array* ans = arrayCreate(intDestroy);
	while (scanf("%d", &n) == 1) {
		solve(n, ans);
	}
	
	for(uint32_t i = 0; i < arraySize(ans); i++){
		int answer = intGet(arrayAt(ans, i));
		printf("%d\n", answer);
	}
	arrayDestroy(ans);

#ifdef _MSC_VER
	_CrtDumpMemoryLeaks();
#endif
	return 0;
}

