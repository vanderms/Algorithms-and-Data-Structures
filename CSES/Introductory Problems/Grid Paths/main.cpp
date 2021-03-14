#include <cstdio>


int counter = 0;
char grid[8][8] = {'\0'};
char input[49];

void solve(int depth, int x, int y);
 bool canReach(int depth, int x, int y);
 bool canGoUp(int depth, int x, int y);
 bool canGoRight(int depth, int x, int y);
 bool canGoLeft(int depth, int x, int y);
 bool canGoDown(int depth, int x, int y);


int main(){
	
    for(int i = 0; i < 49; i++){
    	scanf("%c", &input[i]);	
	}    
    
    solve(0, 0, 6);
    printf("%d", counter);
}

void solve(int depth, int x, int y){
    
	if(depth == 48 && x == 0 && y == 0){
    	counter++;
        return;
    }

    if(!canReach(depth, x, y)){
    	return;
    }

    grid[x][y] = '1';

    if(canGoUp(depth, x, y)){
    	solve(depth + 1, x, y + 1);
    }
    
	if(canGoRight(depth, x, y)){
    	solve(depth + 1, x + 1, y);
    }
    
	if(canGoDown(depth, x, y)){
    	solve(depth + 1, x, y - 1);
    }
    
	if(canGoLeft(depth, x, y)){
    	solve(depth + 1, x - 1, y);
    }

    grid[x][y] = '\0';
}

bool canGoUp(int depth, int x, int y){
    if(y == 6 || grid[x][y + 1] == '1'){
        return false;
	}

    if(!(input[depth] == '?' || input[depth] == 'U')){
            return false;
        }
        return true;
    }

    bool canGoRight(int depth, int x, int y){
        if(x == 6 || grid[x + 1][y] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'R')){
            return false;
        }
        return true;
    }

    bool canGoDown(int depth, int x, int y){
        if(y == 0 || grid[x][y - 1] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'D')){
            return false;
        }
        return true;
    }

    bool canGoLeft(int depth, int x, int y){
        if(x == 0 || grid[x - 1][y] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'L')){
            return false;
        }
        return true;
    }

    bool canReach(int depth, int x, int y){

        if( y > 0 && y < 6 && x > 0 && x < 6){
            bool verticalFree = grid[x][y + 1] == '\0' && grid[x][y - 1] == '\0';
            bool verticalBlocked = grid[x][y + 1] == '1' && grid[x][y - 1] == '1';
            bool HorizontalFree = grid[x + 1][y] == '\0' && grid[x - 1][y] == '\0';
            bool HorizontalBlocked = grid[x + 1][y] == '1' && grid[x - 1][y] == '1';

            if(verticalFree && HorizontalBlocked){
                return false;
            }

            if(verticalBlocked && HorizontalFree){
                return false;
            }
        }

        //grid walls
        if(y == 6 && x > 0 && grid[x - 1][y] == '\0'){
            return false;
        }

        if(x == 6 && y < 6 && grid[x][y + 1] == '\0'){
            return false;
        }

        if(y == 0 && x < 6 && grid[x + 1][y] == '\0'){
            return false;
        }

        if(x == 0 && y < 6 && grid[x][y + 1] == '\0'){
            return false;
        }

        int distance = x + y;
        int remaining = 48 - depth;

        if(remaining < distance){
            return false;
        }

        return true;
    }
