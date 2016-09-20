package org.holgersson.Transaction_Management.model;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import javax.ws.rs.core.MediaType;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class LongMessageBodyWriter implements MessageBodyWriter<List<Long>>{

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			javax.ws.rs.core.MediaType mediaType) {
		
		return List.class.isAssignableFrom(type) && (((ParameterizedType)genericType).getActualTypeArguments()[0].equals(Long.class));
	}


	

	@Override
	public long getSize(List<Long> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void writeTo(List<Long> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {
		

			entityStream.write(t.toString().getBytes());
		
	
		
		
	}

}
