package net.daw.operation;

/**
 *
 * @author AntonioNP
 */
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.EmpresaBean;
import net.daw.dao.EmpresaDao;
import net.daw.helper.Contexto;
import net.daw.helper.Pagination;

public class EmpresaList1 implements Operation {

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contexto oContexto = (Contexto) request.getAttribute("contexto");
        oContexto.setVista("jsp/empresa/list.jsp");
        try {
            EmpresaDao oEmpresaDao = new EmpresaDao(oContexto.getEnumTipoConexion());
            Integer intPages = oEmpresaDao.getPages(oContexto.getNrpp(), oContexto.getAlFilter(), oContexto.getHmOrder());
            if (oContexto.getPage() >= intPages) {
                oContexto.setPage(intPages);
            }
            ArrayList<EmpresaBean> listado = (ArrayList<EmpresaBean>) oEmpresaDao.getPage(oContexto.getNrpp(), oContexto.getPage(), oContexto.getAlFilter(), oContexto.getHmOrder());
            String strUrl = "<a href=\"Controller?" + oContexto.getSerializedParamsExceptPage() + "&page=";
            ArrayList<String> botonera = Pagination.getButtonPad(strUrl, oContexto.getPage(), intPages, 2);
            ArrayList<Object> a = new ArrayList<>();
            a.add(listado);
            a.add(botonera);
            return a;
        } catch (Exception e) {
            throw new ServletException("EmpresaList1: View Error: " + e.getMessage());
        }
    }
}
