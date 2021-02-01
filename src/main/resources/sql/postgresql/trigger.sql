-- 定义updatetime更新函数
create or replace function mt_timestamp() returns trigger as
$$
begin
    new.update_time= current_timestamp;
    return new;
end
$$
language plpgsql;

-- 创建商品库存表的更新时间触发器
create trigger mt_modify before update on t_commodity_stock for each row execute procedure mt_timestamp();