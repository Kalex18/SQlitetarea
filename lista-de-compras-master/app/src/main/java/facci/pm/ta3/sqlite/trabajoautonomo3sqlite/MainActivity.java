package facci.pm.ta3.sqlite.trabajoautonomo3sqlite;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import facci.pm.ta3.sqlite.trabajoautonomo3sqlite.adapter.ShoppingItemAdapter;
import facci.pm.ta3.sqlite.trabajoautonomo3sqlite.database.entities.ShoppingItemDB;
import facci.pm.ta3.sqlite.trabajoautonomo3sqlite.database.model.ShoppingItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private ShoppingItemDB shoppingItemDB;
    private ShoppingItemAdapter shoppingItemAdapter;
    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
Button eliminarItem ;
EditText datoEliminar;

    int datoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.shopping_list_view);

        shoppingItemDB = new ShoppingItemDB(this);

        shoppingItemDB.clearAllItems();

        insertProducts();

        shoppingItems.addAll(shoppingItemDB.getAllItems());

        shoppingItemAdapter = new ShoppingItemAdapter(this, shoppingItems);
        listView.setAdapter(shoppingItemAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_list) {// opccion actualizar la lista de productos
            updateProducts();//actializa la lista de productos
            updateList();//actializa la lista de productos


            return true;
        }

        if (id == R.id.action_clear_list) {// llamada menu iten clear list
           clearList(); // elimina toda la lista
            updateList();// luego la actualiza


            return true;
        }


        if (id == R.id.action_delete_list) {//llamada menu item delete  list
            // en esta parte se despliega una pantalla de dialogo por la cual es enviara la id del producto que
            // se desea eliminar
            final Dialog dbelimnar = new Dialog(MainActivity.this);//se crea un dialog  para main activity
            dbelimnar.setContentView(R.layout.dbeliminar);  // muestra la pantalla del dialogo

             eliminarItem = (Button)dbelimnar.findViewById(R.id.btndeleteIten);
            datoEliminar = (EditText) dbelimnar.findViewById(R.id.txtitem);


// este boton permite hacer la comprobacion de si al presioanr el boton  el iten  a elimianr no este vacio
    eliminarItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            datoint = Integer.parseInt(datoEliminar.getText().toString());

            if (datoEliminar.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "no se encuentra en la lista", Toast.LENGTH_LONG).show();

                dbelimnar.cancel();// metodo para cerra la pantalla de dialogo

            }
            else {


                deleteList(shoppingItems.get(datoint));// metodo delete list , se envia un parametro de clase int

                updateList();// actualiza la lista
                updateProducts();// actualiza los productos

                Toast.makeText(MainActivity.this, "Eliminado correctamente ", Toast.LENGTH_LONG).show();

                dbelimnar.cancel();

            }

        }
    });

            dbelimnar.show();// muestra la pantalla de  dialogo




            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void insertProducts() {// inserta productos a la base de datos
        shoppingItemDB.insertElement("Tomatoes");
        shoppingItemDB.insertElement("Water");
        shoppingItemDB.insertElement("Apples");
        shoppingItemDB.insertElement("Soup");
        shoppingItemDB.insertElement("Coffee");
        shoppingItemDB.insertElement("Bread");
        shoppingItemDB.insertElement("Juice");
        shoppingItemDB.insertElement("Pizza");
        shoppingItemDB.insertElement("Mozzarella");
        shoppingItemDB.insertElement("Onion");
        shoppingItemDB.insertElement("Milk");
        shoppingItemDB.insertElement("Eggs");
        shoppingItemDB.insertElement("Bananas");
        shoppingItemDB.insertElement("Toilet rolls");
        shoppingItemDB.insertElement("Butter");
        shoppingItemDB.insertElement("Carrots");
    }


    private void updateProducts() {
        if (shoppingItems.size() >= 15) {

            shoppingItems.get(8).setName("Cheese");
            shoppingItems.get(9).setName("Jam");
            shoppingItemDB.updateItem(shoppingItems.get(8));
            shoppingItemDB.updateItem(shoppingItems.get(9));

            shoppingItemDB.deleteItem(shoppingItems.get(0));
            shoppingItemDB.deleteItem(shoppingItems.get(1));
            shoppingItemDB.deleteItem(shoppingItems.get(2));
        }
    }

    private void updateList() {
        shoppingItems.clear();
        shoppingItems.addAll(shoppingItemDB.getAllItems());
        shoppingItemAdapter.notifyDataSetChanged();
    }

    private  void  clearList(){

        shoppingItemDB.clearAllItems();

    }

    private  void  deleteList( ShoppingItem item){

        shoppingItemDB.deleteItem(item);

    }









}
