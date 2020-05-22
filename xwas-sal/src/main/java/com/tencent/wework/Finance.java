package com.tencent.wework;


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

public class Finance {
    public native static long NewSdk();


    public native static int Init(long sdk, String corpid, String secret);


    public native static int GetChatData(long sdk, long seq, long limit, String proxy, String passwd, long timeout, long chatData);


    public native static int GetMediaData(long sdk, String indexbuf, String sdkField, String proxy, String passwd, long timeout, long mediaData);


    public native static int DecryptData(long sdk, String encrypt_key, String encrypt_msg, long msg);
	
    public native static void DestroySdk(long sdk);
    public native static long NewSlice();
    /**
     * @brief �ͷ�slice����NewSlice�ɶ�ʹ��
     * @return 
     */
    public native static void FreeSlice(long slice);

    /**
     * @brief ��ȡslice����
     * @return ����
     */
    public native static String GetContentFromSlice(long slice);

    /**
     * @brief ��ȡslice���ݳ���
     * @return ����
     */
    public native static int GetSliceLen(long slice);
    public native static long NewMediaData();
    public native static void FreeMediaData(long mediaData);

    /**
     * @brief ��ȡmediadata outindex
     * @return outindex
     */
    public native static String GetOutIndexBuf(long mediaData);
    /**
     * @brief ��ȡmediadata data����
     * @return data
     */
    public native static byte[] GetData(long mediaData);
    public native static int GetIndexLen(long mediaData);
    public native static int GetDataLen(long mediaData);

    /**
     * @brief �ж�mediadata�Ƿ����
     * @return 1��ɡ�0δ���
     */
    public native static int IsMediaDataFinish(long mediaData);

    static {
        System.loadLibrary("WeWorkFinanceSdk_Java");
    }
}
