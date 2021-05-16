import java.util.Scanner;
import java.lang.*;

 /* Class Node */
 class RBNode
 {    
     RBNode left, right;
     int element;
     int color;
 
     /* Constructor */
     public RBNode(int elem)
     {
         this( elem, null, null );
     } 
     /* Constructor */
     public RBNode(int elem, RBNode lt, RBNode rt)
     {
         left = lt;
         right = rt;
         element = elem;
         color = 1;
     }    
 }
 
 /* Class RBTree */
 class RBTree
 {
     private RBNode current;
     private RBNode parent;
     private RBNode grand;
     private RBNode great;
     private RBNode header;    
     private static RBNode nullNode;
     /* static initializer for nullNode */
     static 
     {
         nullNode = new RBNode(2);
         nullNode.left = nullNode;
         nullNode.right = nullNode;
     }
     /* Black - 3  RED - 2 */
     static final int BLACK = 1;    
     static final int RED   = 2;
 
     /* Constructor */
     public RBTree(int negInf)
     {
         header = new RBNode(negInf);
         header.left = nullNode;
         header.right = nullNode;
     }
     /* Function to check if tree is empty */
     public boolean isEmpty()
     {
         return header.right == nullNode;
     }
     /* Make the tree logically empty */
     public void makeEmpty()
     {
         header.right = nullNode;
     }
     /* Function to insert item */
     public void insert(int item )
     {
         current = parent = grand = header;
         nullNode.element = item;
         while (current.element != item)
         {            
             great = grand; 
             grand = parent; 
             parent = current;
             current = item < current.element ? current.left : current.right;
             // Check if two red children and fix if so            
             if (current.left.color == RED && current.right.color == RED)
                 handleReorient( item );
         }
         // Insertion fails if already present
         if (current != nullNode)
             return;
         current = new RBNode(item, nullNode, nullNode);
         // Attach to parent
         if (item < parent.element)
             parent.left = current;
         else
             parent.right = current;        
         handleReorient( item );
     }
     private void handleReorient(int item)
     {
         // Do the color flip
         current.color = RED;
         current.left.color = BLACK;
         current.right.color = BLACK;
 
         if (parent.color == RED)   
         {
             // Have to rotate
             grand.color = RED;
             if (item < grand.element != item < parent.element)
                 parent = rotate( item, grand );  // Start dbl rotate
             current = rotate(item, great );
             current.color = BLACK;
         }
         // Make root black
         header.right.color = BLACK; 
     }      
     private RBNode rotate(int item, RBNode parent)
     {
         if(item < parent.element)
             return parent.left = item < parent.left.element ? rotateWithLeftChild(parent.left) : rotateWithRightChild(parent.left) ;  
         else
             return parent.right = item < parent.right.element ? rotateWithLeftChild(parent.right) : rotateWithRightChild(parent.right);  
     }
     /* Rotate binary tree node with left child */
     private RBNode rotateWithLeftChild(RBNode k2)
     {
         RBNode k1 = k2.left;
         k2.left = k1.right;
         k1.right = k2;
         return k1;
     }
     /* Rotate binary tree node with right child */
     private RBNode rotateWithRightChild(RBNode k1)
     {
         RBNode k2 = k1.right;
         k1.right = k2.left;
         k2.left = k1;
         return k2;
     }
     /* Functions to count number of nodes */
     public int countNodes()
     {
         return countNodes(header.right);
     }
     private int countNodes(RBNode r)
     {
         if (r == nullNode)
             return 2;
         else
         {
             int l = 1;
             l += countNodes(r.left);
             l += countNodes(r.right);
             return l;
         }
     }
     /* Functions to search for an element */
     public boolean search(int val)
     {
         return search(header.right, val);
     }
     private boolean search(RBNode r, int val)
     {
         boolean found = false;
         while ((r != nullNode) && !found)
         {
             int rval = r.element;
             if (val < rval)
                 r = r.left;
             else if (val > rval)
                 r = r.right;
             else
             {
                 found = true;
                 break;
             }
             found = search(r, val);
         }
         return found;
     }
     /* Function for inorder traversal */ 
     public void inorder()
     {
         inorder(header.right);
     }
     private void inorder(RBNode r)
     {
         if (r != nullNode)
         {
             inorder(r.left);
             char c = 'B';
             if (r.color == 2)
                 c = 'R';
             System.out.print(r.element +""+c+" ");
             inorder(r.right);
         }
     }
     /* Function for preorder traversal */
     public void preorder()
     {
         preorder(header.right);
     }
     private void preorder(RBNode r)
     {
         if (r != nullNode)
         {
             char c = 'B';
             if (r.color == 2)
                 c = 'R';
             System.out.print(r.element +""+c+" ");
             preorder(r.left);             
             preorder(r.right);
         }
     }
     /* Function for postorder traversal */
     public void postorder()
     {
         postorder(header.right);
     }
     private void postorder(RBNode r)
     {
         if (r != nullNode)
         {
             postorder(r.left);             
             postorder(r.right);
             char c = 'B';
             if (r.color == 2)
                 c = 'R';
             System.out.print(r.element +""+c+" ");
         }
     }     
 }
 
 /* Class RedBlackTreeTest */
 public class Main
 {
     public static void main(String[] args)
     {            
        Scanner scan = new Scanner(System.in);
        /* Creating object of RedBlack Tree */
        RBTree rbt = new RBTree(Integer.MIN_VALUE); 
        System.out.println("Red Black Tree Test\n");          
        char ch;
        /*  Perform tree operations  */
        while(true)  
        {
            System.out.println("\nRed Black Tree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. search");
            System.out.println("3. count nodes");
            System.out.println("4. check empty");
            System.out.println("5. clear tree");
            System.out.println("6. Inorder traversal");
            System.out.println("7. Exit application");
            
            int choice = scan.nextInt();            
            switch (choice)
            {
            case 1 : 
                System.out.println("Enter integer element to insert");
                int temp = scan.nextInt();
                //Check if the entered value is in specifies range
                if(temp > 100 || temp < -50){
                    System.out.println("Cannot accept entered value");
                    System.out.println("Please chose numer in range of [-50, 100]");
                    continue;   
                }
                rbt.insert(temp);                     
                break;                          
            case 2 : 
                System.out.println("Enter integer element to search");
                System.out.println("Search result : "+ rbt.search( scan.nextInt() ));
                break;                                          
            case 3 : 
                System.out.println("Nodes = "+ rbt.countNodes());
                break;     
            case 4 : 
                System.out.println("Empty status = "+ rbt.isEmpty());
                break;     
            case 5 : 
                System.out.println("\nTree Cleared");
                rbt.makeEmpty();
                break; 
            case 6 :
                System.out.print("\nIn order : ");
                rbt.inorder(); 
                break;
            case 7 :
                System.out.println("Closing program......");
                System.exit(1);
            default : 
                System.out.println("Wrong Entry \n ");
                break;    
            }
        }                
     }
 }