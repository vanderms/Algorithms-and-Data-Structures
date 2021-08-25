#include <stdio.h>
#include <string.h>
#include <ctype.h>


int main(){
    char c;
    int i = 1;
    while(scanf("%c", &c) == 1){
        if(c == ' ' || c == '\n'){
            printf("%c", c);
            if(c == '\n'){
                i = 1;
            }
        }
        else if(i == 1){
            printf("%c", toupper(c));
            i = 0;
        } else{
            printf("%c", tolower(c));
            i = 1;
        }
    }
}
