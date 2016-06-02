import java.sql.*;
public class CiudadDeLosNiños {
	public static void main(String[] args) {
		try {
//-----------------------------------------------------------------------------------------------------------------------------------------
			//Create conecction
			String driver = "org.postgresql.Driver";
			String url = "jdbc:postgresql://localhost:5432/ciudadDeLosNiños";
			String username = "postgres";
			String password = "root";
			Connection connection = createConnection(driver,url,username,password);
//------------------------------------------------------------------------------------------------------------------------------------------
			//Set patch schema
			String nameSchema = "ciudaddelosniños";
			ResulSetCiudadDeLosNiños.setSchema(nameSchema, connection);
//-------------------------------------------------------------------------------------------------------------------------------------------	  
			// Consultation donante and programs provides.
			ResulSetCiudadDeLosNiños.showDonantesWithAporta(connection);
//-------------------------------------------------------------------------------------------------------------------------------------------
			//Delete a Donante.
			int dni_donante_eliminar = 37108056;
			//ResulSetCiudadDeLosNiños.deleteDonante(dni_donante_eliminar, connection); 
//------------------------------------------------------------------------------------------------------------------------------------------
			//Data to insert a padrino.
			int dni = 37876048;
			String nombre = new String("'Lucas'");
			String apellido = new String("'Armas'");
			String fecha_nac = new String("'20/07/1994'");
			String direccion = new String("'Moldes 441'");
			int cod_postal = 5805;
			String e_mail = new String("'lucas.armas@gmail.com'");
			String facebook = new String("'lucasarmas'");
			int edad = 21;
			int tel_fijo = 4970352;
			int tel_cel = 12343334;
			//Insert a padrino.
			//ResulSetCiudadDeLosNiños.insertPadrino(dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel, connection);
//--------------------------------------------------------------------------------------------------------------------------------------------
			ResulSetCiudadDeLosNiños.showCantAportPrograms(connection);
//--------------------------------------------------------------------------------------------------------------------------------------------
			ResulSetCiudadDeLosNiños.showDonanteAport(connection);
//--------------------------------------------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------------------------------------------
		//catch exceptions.
		}catch(ClassNotFoundException cnfe) {
			System.err.println("Error loading driver: " + cnfe);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		  	System.err.println("Error connecting: " + sqle);
		}/*catch(InvalidDataException s){
				System.out.println(s.getMessage());
		}*/
	}	
}