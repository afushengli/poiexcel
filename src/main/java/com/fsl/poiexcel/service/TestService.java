package com.fsl.poiexcel.service;

import com.fsl.poiexcel.common.ServerResponse;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

}
