import java.sql.*;

public class MetadataCiudadDeLosNiños{
	
	public MetadataCiudadDeLosNiños(){}

	public static void main(String[] args) {
		try{
			String driver = "org.postgresql.Driver";
			String url = "jdbc:postgresql://localhost:5432/implementacion";
			String username = "postgres";
			String password = "root";

			Class.forName(driver);

			Connection connection = DriverManager.getConnection(url,username,password);

			String[] tipo = {"TABLE"};
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSetTables = metaData.getTables("procedimientos","public", null, tipo);
			System.out.println(" tablas de la base de datos ");

			while(resultSetTables.next()) {
          		System.out.println(" catalogo: " + resultSetTables.getString(1));
          		System.out.println(" esquema: " + resultSetTables.getString(2));
          		System.out.println(" nombre: " + resultSetTables.getString(3));
			    System.out.println(" tipo: " + resultSetTables.getString(4));
			    System.out.println(" comentarios: " + resultSetTables.getString(5));
            }
		} catch(ClassNotFoundException cnfe) {
    		System.err.println("Error loading driver: " + cnfe);
    	} catch(SQLException sqle) {
    		sqle.printStackTrace();
      		System.err.println("Error connecting: " + sqle);
    	}

	}
}