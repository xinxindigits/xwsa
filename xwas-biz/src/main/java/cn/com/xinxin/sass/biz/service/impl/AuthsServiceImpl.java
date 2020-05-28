package cn.com.xinxin.sass.biz.service.impl;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import cn.com.xinxin.sass.biz.service.AuthsService;
import cn.com.xinxin.sass.repository.dao.AuthsDOMapper;
import cn.com.xinxin.sass.repository.model.AuthsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 26/05/2020.
 * @updater:
 * @description:
 */
@Service
public class AuthsServiceImpl implements AuthsService {


    @Autowired
    private AuthsDOMapper authsDOMapper;



    @Override
    public List<AuthsDO> selectAllAuths() {
        List<AuthsDO> authsDOS = this.authsDOMapper.selectAllAuths();
        return authsDOS;
    }

    @Override
    public int createAuths(AuthsDO authsDO) {

        int result = this.authsDOMapper.insertSelective(authsDO);

        return result;
    }

    @Override
    public int updateAuths(AuthsDO authsDO) {

        int result = this.authsDOMapper.updateByPrimaryKey(authsDO);
        return result;
    }

    @Override
    public int deleteById(Long id) {

        int result = this.authsDOMapper.deleteByPrimaryKey(id);

        return result;
    }
}
