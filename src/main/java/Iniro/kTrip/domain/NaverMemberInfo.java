package Iniro.kTrip.domain;

import java.util.Map;

public class NaverMemberInfo implements OAuth2MemberInfo{
    private Map<String, Object> attributes;
    public NaverMemberInfo(Map<String, Object> attributes){
        this.attributes=attributes;
    }

    @Override
    public String getProviderId(){
        return (String)attributes.get("sub");
    }

    @Override
    public String getProvider(){
        return "naver";
    }
    @Override
    public String getName(){
        return (String) attributes.get("name");
    }
    @Override
    public String getEmail(){
        return (String) attributes.get("email");
    }
}
