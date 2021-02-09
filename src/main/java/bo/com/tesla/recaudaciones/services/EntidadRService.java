package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dao.IEntidadDao;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;
import bo.com.tesla.security.dao.ISegUsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadRService implements IEntidadRService {
/*
    @Override
    public Optional<List<EntidadDto>> getByRecaudadoraIdAndTipoEntidadId(Long recaudadoraId, Long tipoEntidadId) {
        return IEntidadRDao.findByRecaudadoraIdAndTipoEntidadId(recaudadoraId, tipoEntidadId);
    }

    @Override
    public Optional<List<EntidadDto>> getAllByRecaudadoraId(Long recaudadoraId) {
        return IEntidadRDao.findByRecaudadoraId(recaudadoraId);
    }

    @Override
    public Optional<List<DominioDto>> getTipoEntidadByRecaudador(Long recaudadoraId) {
        return IEntidadRDao.findTipoEntidadByRecaudadorId(recaudadoraId);
    }


    /////////

    */

    @Autowired
    private IEntidadRDao IEntidadRDao;

    @Autowired
    private IRecaudadorDao iRecaudadorDao;

    @Autowired
    private ISegUsuarioDao usuarioDao;

    @Override
    public Optional<List<EntidadDto>> getByRecaudadoraIdAndTipoEntidadIdA(Long tipoEntidadId, String login) {
        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        return IEntidadRDao.findByRecaudadoraIdAndTipoEntidadId(recaudadorEntity.getRecaudadorId(), tipoEntidadId);
    }

    @Override
    public Optional<List<EntidadDto>> getAllByRecaudadoraIdA(String login) {
        SegUsuarioEntity usuario =this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = this.iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        return IEntidadRDao.findByRecaudadoraId(recaudadorEntity.getRecaudadorId());
    }

    @Override
    public Optional<List<DominioDto>> getTipoEntidadByRecaudadorA(String login) {
        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        return IEntidadRDao.findTipoEntidadByRecaudadorId(recaudadorEntity.getRecaudadorId());
    }



}
