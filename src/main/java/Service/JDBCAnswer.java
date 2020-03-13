package Service;

import Tables.ResultTable;

import java.sql.*;
import java.util.*;

public class JDBCAnswer {

    private static List<ResultTable> resultTableList;

    private String brandType;
    private int partnum;

    public JDBCAnswer(int brandTypeId, int partnum){
        resultTableList = new ArrayList<>();
        this.brandType = BrandType.getBrandType(brandTypeId);
        this.partnum = partnum;

    }

    private void createResultTableList(){
        final String passsword = "1234";
        final String postgresUser = "postgres";
        final String urlAdress = "jdbc:postgresql://127.0.0.1:5432/catalogzapchasti";

        String SQL_SELECT = String.format("SELECT public.parts.brantype AS brandType, " +
                "public.parts.partnum AS partNumber, " +
                "public.partsvehicles.altpartnum AS altPartNum, " +
                "public.parts.brand AS brand, " +
                "public.parts.descr AS description, " +
                "public.price.rrp AS rrp, " +
                "public.price.pridate AS priceDate, " +
                "public.parts.artstat AS articleStatus  " +
                "FROM public.country JOIN public.price " +
                "ON public.country.countryId = public.price.countryId " +
                "JOIN public.parts ON public.price.partnum = public.parts.partnum " +
                "JOIN public.partsvehicles  ON public.parts.faltpartnum = public.parts.altpartnum " +
                "JOIN public.vehicles ON public.partsvehicles.carid = public.vehicles.carid " +
                "WHERE public.parts.partnum = %d AND public.parts.brandrype = %s", this.partnum, this.brandType);

        try(Connection conn = DriverManager.getConnection(
                urlAdress, postgresUser, passsword);
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                String brandType = resultSet.getString("brandType");
                String partNumber = resultSet.getString("partNumber");
                String altPartNum = resultSet.getString("altPartNum");
                String brand = resultSet.getString("brand");
                String description = resultSet.getString("description");
                double rrp = resultSet.getDouble("rrp");
                String priceDate = resultSet.getString("priceDate");
                int numberOfReferences = resultSet.getInt("numOfRef");
                int numberOfVehiclesLinkings = resultSet.getInt("numOfVehLinks");
                String articleStatus = resultSet.getString("articleStatus");

                ResultTable resultTable = new ResultTable();
                resultTable.setBrandType(brandType);
                resultTable.setPartNumber(partNumber);
                resultTable.setAltPartNum(altPartNum);
                resultTable.setBrand(brand);
                resultTable.setDescription(description);
                resultTable.setRrp(rrp);
                resultTable.setPriceDate(priceDate);
                resultTable.setNumberOfReferences(numberOfReferences);
                resultTable.setNumberOfVehiclesLinkings(numberOfVehiclesLinkings);
                resultTable.setArticleStatus(articleStatus);

                resultTableList.add(resultTable);

            }

        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public List<ResultTable> getResultTableList(){
        createResultTableList();
        return resultTableList;
    }



}
