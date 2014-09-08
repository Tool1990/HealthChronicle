package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@SessionScoped
@ManagedBean
public class OptionsBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Map<String, Object> languages;
	static
	{
		languages = new LinkedHashMap<String, Object>();
		languages.put("german", Locale.GERMAN);
		languages.put("english", Locale.ENGLISH);
	}

	public Map<String, Object> getLanguages()
	{
		return languages;
	}

	public void localeCodeChanged(ValueChangeEvent e)
	{
		String newLocaleValue = e.getNewValue().toString();

		for (Map.Entry<String, Object> entry : languages.entrySet())
		{
			if (entry.getValue().toString().equals(newLocaleValue))
			{
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
				return;
			}
		}
	}
}
