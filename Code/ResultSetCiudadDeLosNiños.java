import java.sql.*;
import java.util.Scanner;
public class ResultSetCiudadDeLosNiños {
	
	public static Connection createConnection( String driver, String url, String username, String password)throws ClassNotFoundException, SQLException {
		// Load database driver if not already loaded.
		Class.forName(driver);
		// Establish network connection to database.
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
	public static void setSchema(String nameSchema, Connection connection)throws ClassNotFoundException, SQLException {
		String query = "SET search_path TO " + nameSchema + "";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}

	public static void showDonantesWithAporta(Connection connection)throws ClassNotFoundException, SQLException {
		System.out.println("Mostrando donantes con sus aportes");
		String query = "SELECT p.dni, p.nombre, p.apellido, pr.nombre, a.monto, a.frecuencia FROM persona p, aporta a, programa pr WHERE p.dni=a.dni_donante AND a.id_programa=pr.id_programa";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
		    System.out.print("\n");
		    System.out.print(" DNI: " + resultSet.getString(1)+"\n");
		    System.out.print(" Nombre: " + resultSet.getString(2)+"\n");
		    System.out.print(" Apellido: " + resultSet.getString(3)+"\n");
		    System.out.print(" Programa: " + resultSet.getString(4)+"\n");
		    System.out.print(" Monto: " + resultSet.getString(5)+"\n");
		    System.out.print(" Frecuencia: " + resultSet.getString(6)+"\n");
			System.out.print("\n");
			System.out.print("\n");
		}
	}

	public static void deleteDonante(Connection connection)throws ClassNotFoundException, SQLException, InvalidDataException {
		System.out.println("Inserte el dni del donante a eliminar");
		Scanner sc = new Scanner(System.in);
		int dni_donante = sc.nextInt();
		if (alreadyExistsDonante(dni_donante,connection)){
			String query = "DELETE FROM donante WHERE donante.dni = "+dni_donante+"";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Donante deleted");
		}else{
			throw new InvalidDataException("DELETE DONANTE: Donante does not exists.");
		}																
	}

	public static void insertPadrino(Connection connection)throws ClassNotFoundException, SQLException, InvalidDataException {
		Scanner sci = new Scanner(System.in);
		System.out.println("Ingrese el dni del padrino"+"\n");
		int dniAux = sci.nextInt();
		if (!alreadyExistsPadrino(dniAux,connection)){
			Scanner scs = new Scanner(System.in);
			System.out.println("Ingrese el nombre del padrino");
			String nombreAux = scs.nextLine();
			System.out.println("Ingrese el apellido del padrino");
			String apellidoAux = scs.nextLine();
			System.out.println("Ingrese la fecha de nacimiento del padrino");
			String fecha_nacAux = scs.nextLine();
			System.out.println("Ingrese la direccion del padrino encerrada entre '...'");
			String direccionAux = scs.nextLine();
			System.out.println("Ingrese el codigo postal del padrino");
			int cod_postalAux = sci.nextInt();
			System.out.println("Ingrese la direccion de e-mail del padrino encerrada entre '...'");
			String e_mailAux = scs.nextLine();
			System.out.println("Ingrese el facebook del padrino");
			String facebookAux = scs.nextLine();
			System.out.println("Ingrese la edad del padrino");
			int edadAux = sci.nextInt();
			System.out.println("Ingrese el telefono fijo del padrino");
			int tel_fijoAux = sci.nextInt();
			System.out.println("Ingrese el telefono celular del padrino");
			int tel_celAux = sci.nextInt();

			String query = "INSERT INTO persona (dni, nombre, apellido, fecha_nac, direccion, cod_postal, e_mail, facebook, edad, tel_fijo, tel_cel)"
							+ " VALUES ("+dniAux+", "+nombreAux+", "+apellidoAux+", "+fecha_nacAux+", "+direccionAux+", "+cod_postalAux+", "+e_mailAux+", "+facebookAux+", "+edadAux+", "+tel_fijoAux+", "+tel_celAux+")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);

			query ="INSERT INTO padrino (dni) VALUES("+dniAux+")";
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Padrino added."+"\n");
		}else{
			throw new InvalidDataException("INSERT PADRINO: padrino already exists.");
		}
	}

	public static boolean alreadyExistsPadrino(int dni_padrino, Connection connection)throws ClassNotFoundException, SQLException {
		String query = "SELECT DISTINCT dni FROM padrino WHERE dni = "+dni_padrino;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		boolean exists = resultSet.next();
		return exists;
	}

	public static boolean alreadyExistsDonante(int dni_donante, Connection connection)throws ClassNotFoundException, SQLException {
		String query = "SELECT DISTINCT dni FROM donante WHERE dni = "+dni_donante;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		boolean exists = resultSet.next();
		return exists;
	}

	public static void showCantAportPrograms(Connection connection)throws ClassNotFoundException, SQLException {
		System.out.println("Mostrando programas con el total de su monto mensual \n");
		System.out.println("INFO: Si un programa no figura en la lista no recibe aportes. \n");
		String query = "SELECT pr.id_programa, pr.nombre, ap.monto_total "+
					   "FROM programa pr,(SELECT id_programa, SUM(monto) AS monto_total FROM aporta GROUP BY id_programa) ap "+
					   "WHERE pr.id_programa=ap.id_programa "+
					   "ORDER BY pr.id_programa ASC";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
		    System.out.print("\n");
		    System.out.print(" ID_Programa: " + resultSet.getString(1)+"\n");
		    System.out.print(" Nombre: " + resultSet.getString(2)+"\n");
		    System.out.print(" Monto total por mes: " + resultSet.getString(3)+"\n");
			System.out.print("\n");
			System.out.print("\n");
		}
	}

	public static void showDonanteAport(Connection connection)throws ClassNotFoundException, SQLException {
		System.out.println("Mostrando donantes que aportan a mas de un programa");
		String query = "SELECT p.dni, p.nombre, p.apellido "+
					   "FROM persona p, (SELECT DISTINCT dni_donante FROM aporta "+
									     "WHERE dni_donante IN (SELECT dni_donante FROM aporta "+
									   	       					"GROUP BY dni_donante "+
									   						    "HAVING COUNT(dni_donante) > 1)) ap "+
					   	"WHERE p.dni = ap.dni_donante";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
		    System.out.print("\n");
		    System.out.print(" DNI: " + resultSet.getString(1)+"\n");
		    System.out.print(" Nombre: " + resultSet.getString(2)+"\n");
		    System.out.print(" Apellido: " + resultSet.getString(3)+"\n");
			System.out.print("\n");
			System.out.print("\n");
		}
	}

	public static void showDonanteAportMedioPago(Connection connection)throws ClassNotFoundException, SQLException {
		//TODO: Implementar consulta que devuelva el listado de todos los donantes con sus aportes mensuales y los datos
		//     de los medios de pago.
		System.out.println("Mostrando donantes y aporte total");
		String queryAux1 = "SELECT cod_pago, nombre_tarjeta, numero_tarjeta, vencimiento, cod_verificacion, 'no aplica' AS nombre_banco, "+
		                   "'no aplica' AS sucursal_banco, 'no aplica' AS tipo, null AS nro_cuenta, null AS cbu FROM  tarjeta";
		
		String queryAux2 = 	"SELECT cod_pago, 'no aplica' AS nombre_tarjeta, null AS numero_tarjeta, null AS vencimiento, "+
						    "null AS cod_verificacion, nombre_banco, sucursal_banco, tipo, nro_cuenta, cbu FROM  debito_transferencia";		   
		
		String queryAux3 = "SELECT p.dni, p.nombre, p.apellido, SUM(ap.monto) AS monto_total, m.cod_pago, m.nombre_tarjeta, m.numero_tarjeta, "+
						   "m.vencimiento, m. cod_verificacion, m. nombre_banco, m.sucursal_banco, m.tipo, m.nro_cuenta, m.cbu "+
						   "FROM aporta ap, ("+queryAux1+" UNION "+queryAux2+") m, persona p "+
						   "WHERE (ap.dni_donante = p.dni) AND (ap.cod_pago = m.cod_pago) GROUP BY p.dni, ap.monto, m.cod_pago, m.nombre_tarjeta, m.numero_tarjeta, m.vencimiento, m. cod_verificacion, m. nombre_banco, m.sucursal_banco, m.tipo, m.nro_cuenta, m.cbu";
		//String query = 
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(queryAux3);



		while(resultSet.next()) {
		    System.out.print("\n");
		    System.out.print(" DNI: " + resultSet.getString(1)+"\n");
		    System.out.print(" Nombre: " + resultSet.getString(2)+"\n");
		    System.out.print(" Apellido: " + resultSet.getString(3)+"\n");
		    System.out.print(" Aporte total: " + resultSet.getString(4)+"\n");
		   	System.out.print(" Codigo de pago: " + resultSet.getString(5)+"\n");
		    System.out.print(" Nombre tarjeta: " + resultSet.getString(6)+"\n");
		   	System.out.print(" Numero de tarjeta: " + resultSet.getString(7)+"\n");
		   	System.out.print(" Vencimiento: " + resultSet.getString(8)+"\n");
		   	System.out.print(" Codigo de verificacion: " + resultSet.getString(9)+"\n");
		   	System.out.print(" Nombre banco: " + resultSet.getString(10)+"\n");
		   	System.out.print(" Sucursal banco: " + resultSet.getString(11)+"\n");
		   	System.out.print(" Tipo: " + resultSet.getString(12)+"\n");
		   	System.out.print(" Numero de cuenta: " + resultSet.getString(13)+"\n");
		   	System.out.print(" CBU: " + resultSet.getString(14)+"\n");
			System.out.print("\n");
		}
	} 

	
	public static void menuOpciones(Connection connection)throws ClassNotFoundException, SQLException, InvalidDataException{
		int selection=0;
		while (selection!=6){
			System.out.println("Ingrese el numero de la operacion que quiere realizar");
			System.out.println("1) Insertar un padrino.");
			System.out.println("2) Eliminar un donante.");
			System.out.println("3) Ver donantes que aportan a mas de un programa.");
			System.out.println("4) Ver los aportes que recibe cada programa.");
			System.out.println("5) Ver los donantes con sus aportes.");
			System.out.println("6) Ver los donantes con su aporte total y su medio de pago.");
			System.out.println("7) Salir.");
			Scanner sc = new Scanner(System.in);
			selection = sc.nextInt();
			if(selection==1){insertPadrino(connection);};
			if(selection==2){deleteDonante(connection);};
			if(selection==3){showDonanteAport(connection);};
			if(selection==4){showCantAportPrograms(connection);};
			if(selection==5){showDonantesWithAporta(connection);};
			if(selection==6){showDonanteAportMedioPago(connection);};
			if((selection<=0)||(selection>7)){System.out.println("Opcion incorrecta");};
		}
	}

	public static void main(String[] args) {
		try {
//------------------Create conecction-----------------------------------------------------------------------------------------------------------------------
			String driver = "org.postgresql.Driver";
			String url = "jdbc:postgresql://localhost:5432/ciudadDeLosNiños";
			String username = "postgres";
			String password = "root";
			Connection connection = createConnection(driver,url,username,password);

//------------------Set patch schema------------------------------------------------------------------------------------------------------------------------
			String nameSchema = "ciudaddelosniños";
			setSchema(nameSchema, connection);
/*
//----------------Consultations donante and programs provides---------------------------------------------------------------------------------------------------------------------------	  
			showDonantesWithAporta(connection);

//-------------------------------------------------------------------------------------------------------------------------------------------
			showCantAportPrograms(connection);

//--------------------------------------------------------------------------------------------------------------------------------------------
			showDonanteAport(connection);

//--------------------------------------------------------------------------------------------------------------------------------------------
			//showDonanteAportMedioPago(connection);

//----------------Delete a Donante----------------------------------------------------------------------------------------------------------------------------
			//int dni_donante_eliminar = 37108056;
			//deleteDonante(connection); 

//----------------Data to insert a padrino--------------------------------------------------------------------------------------------------------------------------
			/*
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
			*/
//------------------Insert a padrino----------------------------------------------------------------------------------------------------------
			//insertPadrino(connection);
			/**/
			menuOpciones(connection);
//------------------Catch exceptions--------------------------------------------------------------------------------------------------------------------------
		}catch(ClassNotFoundException cnfe) {
			System.err.println("Error loading driver: " + cnfe);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		  	System.err.println("Error connecting: " + sqle);
		}catch(InvalidDataException s){
				System.out.println(s.getMessage());
		}
	}
}