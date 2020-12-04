#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

typedef struct tree {
    char data;
    struct tree* left;
    struct tree* right;
}*Tree;

Tree initTree(char data) {
	Tree tree = malloc(sizeof(struct tree));
	tree->data = data;
	tree->left = NULL;
	tree->right = NULL;
	return tree;
}

void add(Tree root, char c, int from, int to) {
    if (root == NULL)
        return;
    //if it's a variable, it must be a leaf
    if (root->data >= 'A' && root->data <= 'Z')
            return;
    //we're working only with one level beneath
    if (to - from == 1) {
        //if our node has a '~', it will only have a left child
        if (root->data == '~' && root->left == NULL) {
            root->left = initTree(c);
            return;
        } else{ 
            if (root->left == NULL) {
                root->left = initTree(c);
                return;
            }
            if (root->right == NULL && root->data != '~') {
                root->right = initTree(c);
                return;
            }
        }
    }
    //used divide et impera to reach the level we need to work on
    add(root->left, c, from + 1, to);
    add(root->right, c, from + 1, to);
}

Tree clone(Tree tree) {
    if (tree != NULL) {
        Tree aux = initTree(tree->data);
        aux->left = clone(tree->left);
        aux->right = clone(tree->right);
        return aux;
    }
}

void convert(Tree tree) {
    if (tree->data >= 'A' && tree->data <= 'Z')
        return;

    if (tree->data == '^') {
        convert(tree->left);
        convert(tree->right);
        return;
    }

    if (tree->data == 'v') {
        //if both are leaves, than the form is CNF and we leave it as it is
        if ((tree->left->data >= 'A' && tree->left->data <= 'Z') &&
            (tree->right->data >= 'A' && tree->right->data <= 'Z')) {
                return;
            }
        //in this case we have 4 branches that will be converted into 8
        if (tree->left->data == '^' && tree->right->data == '^') {
            Tree aux[8];
            aux[0] = clone(tree->left->left); 
            aux[1] = clone(tree->left->left); 
            aux[2] = clone(tree->left->right);
            aux[3] = clone(tree->left->right);
            aux[4] = clone(tree->right->left);
            aux[5] = clone(tree->right->left);
            aux[6] = clone(tree->right->right);
            aux[7] = clone(tree->right->right);

            //root
            tree->data = '^';
            //1st level
            tree->left->data = '^';
            tree->right->data = '^';
            //2nd level
            tree->left->left->data = 'v';
            tree->left->right->data = 'v';
            tree->right->left->data = 'v';
            tree->right->right->data = 'v';
            //3rd level
            tree->left->left->left = aux[0];
            tree->left->left->right = aux[4];
            tree->left->right->left = aux[1];
            tree->left->right->right = aux[6];
            tree->right->left->left = aux[2];
            tree->right->left->right = aux[5];
            tree->right->right->left = aux[3];
            tree->right->right->right = aux[7];

            convert(tree->left->left->left);
            convert(tree->left->left->right);
            convert(tree->left->right->left);
            convert(tree->left->right->right);
            convert(tree->right->left->left);
            convert(tree->right->left->right);
            convert(tree->right->right->left);
            convert(tree->right->right->right);
            return;
            
        }

        if (tree->left->data == '^') {
            Tree aux[4];
            aux[0] = clone(tree->left->left);
            aux[1] = clone(tree->left->right);
            aux[2] = clone(tree->right);
            aux[3] = clone(tree->right);

            //root
            tree->data = '^';
            //1st level
            tree->left->data = 'v';
            tree->right->data = 'v';
            //2nd level
            tree->left->left = aux[0];
            tree->left->right = aux[2];
            tree->right->left = aux[1];
            tree->right->right = aux[3];

            convert(tree->left->left);
            convert(tree->left->right); 
            convert(tree->right->left); 
            convert(tree->right->right);
            return;
        }

        if (tree->right->data == '^') {
            Tree aux[4];
            aux[0] = clone(tree->right->left); 
            aux[1] = clone(tree->right->right);
            aux[2] = clone(tree->left); 
            aux[3] = clone(tree->left); 

             //root
            tree->data = '^';
            //1st level
            tree->left->data = 'v';
            tree->right->data = 'v';
            //2nd level
            tree->left->left = aux[0];
            tree->left->right = aux[2];
            tree->right->left = aux[1];
            tree->right->right = aux[3];

            convert(tree->left->left);
            convert(tree->left->right); 
            convert(tree->right->left); 
            convert(tree->right->right);
            return;
        }
    }   
    
    if (tree->data == '~') {
        if (tree->left->data >= 'A' && tree->left->data <= 'Z') {
            return;
        }
        if (tree->left->data == '~') {
            Tree aux = tree;
            tree = clone(tree->left->left);
            free(aux->left);
            free(aux);
            convert(tree);
            return;
        }
        if (tree->left->data == '^') {
            tree->data = 'v';
            tree->left->data = '~';
            tree->right = initTree('~');
            tree->right->left = tree->left->right;
            tree->left->right = NULL;
            convert(tree);
            return;
        }
        if (tree->left->data == 'v') {
            tree->data = '^';
            tree->left->data = '~';
            tree->right = initTree('~');
            tree->right->left = tree->left->right;
            tree->left->right = NULL;
            convert(tree);
            return;
        }
    } 
}

//funtion to change the leaves from variables to input values
void changeVarToValue(Tree tree, char *var, int *val, int varNr) {
    if (tree != NULL) {
        for (int i = 0; i < varNr; i++) {
            if (tree->data == var[i]) {
                tree->data = val[i];
            }
        }
        changeVarToValue(tree->left, var, val, varNr);
        changeVarToValue(tree->right, var, val, varNr);
    }
}

//execute the operations
int solveCircuit(Tree tree) {
    if (tree->data == '^')
        return (solveCircuit(tree->left) && solveCircuit(tree->right));
    if (tree->data == 'v')
        return (solveCircuit(tree->left) || solveCircuit(tree->right));
    if (tree->data == '~')
        return !solveCircuit(tree->left);
    return tree->data;
}

int main() {
    char var[30]; //array of variables
    int varNr = 0; //number of variables
    char buff[10]; //buffer
    int i, j; //the level that we're going to add a char on
    Tree tree;
    int ok;
    FILE *f;
    f = fopen("input7.in", "r");
    fscanf(f, "%s", buff);
    tree = initTree(buff[0]);
    //completing the tree while reading the file
    while (fscanf(f, "%s", buff) != EOF) {
        i = 0;
        while (buff[i] == '-') {
            i++;
        }

        if (buff[i] >= 'A' && buff[i] <= 'Z') 
            var[buff[i] - 65] = buff[i];

        add(tree, buff[i], 0, i);
    }
    fclose(f);
    convert(tree);

    //getting the number of variables
    while (var[varNr] == varNr + 65) {
        varNr++;
    }

    int comb[varNr];
    int x; //auxiliary variable to avoid casting
    // generate all the possible combinations of inputs - the table of truth
    for (i = 0; i < pow(varNr, 2); i++) {
        for (j = varNr - 1; j >= 0; j--) {
            x = pow(2, j);
            comb[varNr - 1 - j] = (i/x)%2;
        }
        /*Create clone trees with input as leaves until we find a combination
        that solves the circuit*/
        Tree auxTree = clone(tree);
        for(j = 0; j < varNr; j++) {
            changeVarToValue(auxTree, var, comb, varNr);
        }

        ok = solveCircuit(auxTree);
        if (ok) {
            f = fopen("output.out", "w");
            for (j = 0; j < varNr; j++) {
                fprintf(f, "%c ", var[j]);
            }
            fprintf(f, "\n");
            for (j = 0; j < varNr; j++) {
                fprintf(f, "%d ", comb[j]);
            }
            fprintf(f, "\n");
            fclose(f);
            i = pow(varNr, 2) + 1;
        }
    }
    if (!ok) {
        f = fopen("output.out", "w");
        fprintf(f, "IMPOSIBIL\n");
        fclose(f);
    }
}