package bo.com.tesla.recaudaciones.services;

import java.util.List;
import java.util.Optional;

import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;
import bo.com.tesla.security.dao.ISegUsuarioDao;

@Service
public class EntidadRService implements IEntidadRService {

    @Autowired
    private IEntidadRDao IEntidadRDao;

    @Autowired
    private IRecaudadorDao iRecaudadorDao;

    @Autowired
    private IDominioDao iDominioDao;

    @Transactional(readOnly = true)
    @Override
    public List<EntidadDto> getEntidadesByTipoEntidad(Long tipoEntidadId, SegUsuarioEntity usuario) throws Technicalexception{
        Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        if(!recaudadorEntityOptional.isPresent()) {
            throw new Technicalexception("El usuario " + usuario.getLogin() + "no esta registrado en ninguna sucursal de recaudadción");
        }
        return IEntidadRDao.findByRecaudadoraIdAndTipoEntidadId(recaudadorEntityOptional.get().getRecaudadorId(), tipoEntidadId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EntidadDto> getByRecaudadoraId(SegUsuarioEntity usuario) throws Technicalexception{
        Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        if(!recaudadorEntityOptional.isPresent()) {
            throw new Technicalexception("El usuario " + usuario.getLogin() + "no esta registrado en ninguna sucursal de recaudadción");
        }
        return IEntidadRDao.findByRecaudadoraId(recaudadorEntityOptional.get().getRecaudadorId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DominioDto> getTipoEntidadByRecaudador(SegUsuarioEntity usuario) throws Technicalexception{
        try {
            Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
            if(!recaudadorEntityOptional.isPresent()) {
                throw new Technicalexception("El usuario " + usuario.getLogin() + "no esta registrado en ninguna sucursal de recaudadción");
            }
            List<DominioDto> dominioDtos = iDominioDao.findTipoEntidadByRecaudadorId(recaudadorEntityOptional.get().getRecaudadorId());
            if(dominioDtos.isEmpty()) {
                throw new Technicalexception("No existe TiposEntidad por RecaudadoraId=" + recaudadorEntityOptional.get().getRecaudadorId());
            }
            return dominioDtos;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }



}
