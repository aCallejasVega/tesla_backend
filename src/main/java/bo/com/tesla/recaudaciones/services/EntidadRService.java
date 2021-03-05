package bo.com.tesla.recaudaciones.services;

import java.util.List;
import java.util.Optional;

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
    private ISegUsuarioDao usuarioDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<EntidadDto>> getByRecaudadoraIdAndTipoEntidadId(Long tipoEntidadId, String login) {
        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        return IEntidadRDao.findByRecaudadoraIdAndTipoEntidadId(recaudadorEntity.getRecaudadorId(), tipoEntidadId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<EntidadDto>> getByRecaudadoraId(String login) {
        SegUsuarioEntity usuario =this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = this.iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());       
        return IEntidadRDao.findByRecaudadoraId(recaudadorEntity.getRecaudadorId());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<DominioDto>> getTipoEntidadByRecaudador(String login) {
        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);
        RecaudadorEntity recaudadorEntity = iRecaudadorDao.findRecaudadorByUserId(usuario.getUsuarioId());
        return IEntidadRDao.findTipoEntidadByRecaudadorId(recaudadorEntity.getRecaudadorId());
    }



}
